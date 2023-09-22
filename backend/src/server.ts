import express from 'express';
import cors from 'cors'
import bodyParser from 'body-parser'
import mongoose from 'mongoose';
import http from 'http';
import userRouter from './routers/user.routes';
import imageRouter from './routers/image.routes';
import postRouter from './routers/post.routes';
import pageRouter from './routers/page.routes';
import jobRouter from './routers/job.routes';
import chatRouter from './routers/chat.routes';
import Chat from './models/chat'

const app = express();
app.use(cors())
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({
	extended: false
}));

mongoose.connect('mongodb://localhost:27017/workhubdb');
const connect = mongoose.connection;
connect.once('open', () => {
	console.log('db connection ok');
})

const router = express.Router();
router.use('/user', userRouter);
router.use('/image', imageRouter);
router.use('/post', postRouter);
router.use('/page', pageRouter);
router.use('/job', jobRouter);
router.use('/chat', chatRouter);

app.use(express.json());
app.use('/', router);

app.get('/', (req, res) => {
	res.send('Hello World!')
})

const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);

io.on('connection', (socket) => {
	console.log('user connected')

	socket.on('disconnect', () => {
		console.log('user disconnected');
	});

	socket.on('new chat', (user1, user2) => {
		const chat = new Chat({
			user1: user1,
			user2: user2,
			timestamp: Date.now()
		})

		chat.save().then(chat => {
			io.emit("chat created", chat._id, user1, user2, chat.timestamp);
		})
	})

	socket.on('send message', (user, text, timestamp, chat_id) => {
		Chat.findOneAndUpdate({
			_id: chat_id
		}, {
			$push: { messages: { user: user, text: text, timestamp: timestamp } },
			// timestamp: timestamp,
		}, (err) => {
			if (err)
				console.log(err)

			Chat.findOne({
				_id: chat_id
			}, (err, chat) => {
				let other_user

				if (chat.user1 == user)
					other_user = chat.user2
				else
					other_user = chat.user1

				io.emit("receive message", user, other_user, text, timestamp, chat_id);
			})
		})
	});

	// socket.broadcast.emit('hi');
});

server.listen(4000, () => console.log('listening on localhost:4000'));

import express from 'express';
import cors from 'cors'
import bodyParser from 'body-parser'
import mongoose from 'mongoose';
import userRouter from './routers/user.routes';
import imageRouter from './routers/image.routes';
import postRouter from './routers/post.routes';
import pageRouter from './routers/page.routes';
import jobRouter from './routers/job.routes';

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

app.use(express.json());
app.use('/', router);

app.get('/', (req, res) => {
    res.send('Hello World!')
})

app.listen(4000, () => console.log(`Express server running on port 4000`));
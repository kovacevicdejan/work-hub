import express from 'express';
import cors from 'cors'
import bodyParser from 'body-parser'
import mongoose from 'mongoose';

const app = express();
app.use(cors())
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({
    extended: false
}));

mongoose.connect('mongodb://localhost:27017/workhub');
const connect = mongoose.connection;
connect.once('open', () => {
    console.log('db connection ok');
})

const router = express.Router();

app.use('/', router);
app.get('/', (req, res) => {
    res.send('Hello World!')
  })

app.listen(4000, () => console.log(`Express server running on port 4000`));
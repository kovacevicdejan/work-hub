import express from 'express'
import path from 'path';
const imageRouter = express.Router()
const multer = require('multer');

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
      cb(null, 'images/'); // Specify the directory where uploaded image files will be stored
  },
  filename: (req, file, cb) => {
      cb(null, file.originalname);
  },
});

const upload = multer({ storage });

imageRouter.post('/upload', upload.single('image'), (req, res) => {
  // The uploaded file is available as req.file
  // It will be saved in the 'images/' folder

  res.status(200).send('File uploaded successfully');
});

imageRouter.route('/get_image/:name').get((req, res) => {
  const name = req.params.name;
  const imagePath = path.join(__dirname, '../../images', name);
  res.sendFile(imagePath);
})

export default imageRouter

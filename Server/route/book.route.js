import bookModel from '../model/book.model.js';
import bodyParser from 'body-parser';
import express from 'express';

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }));

router.get('/onsale', async (req, res) => {
  try {
    const ret = await bookModel.getBookOnSale();
    res.send({ arrBookOnSale: ret });
  } catch (err) {
    console.log(err);
    res.send({
      exitcode: 500,
    });
  }
});

router.post('/getDetails', async (req, res) => {
  try {
    const ret = await bookModel.getBook(req.body.id);
    res.send({
      _id: ret._id,
      id: ret.id,
      name: ret.name,
      author: ret.author,
      distributor: ret.distributor,
      seller: ret.seller,
      price: ret.price,
      saleprice: ret.saleprice,
      category: ret.category,
      picture: ret.picture,
      release_year: ret.release_year,
      description: ret.description,
      quantity: ret.quantity,
      state: ret.state,
      star: ret.star,
      comments: ret.comments,
    });
  } catch (err) {
    console.log(err);
    res.send({
      exitcode: 500,
    });
  }
});

router.get('/', async (req, res) => {
  try {

    const ret = await bookModel.findAll();
    res.send({ arrBook: ret });
  } catch (error) {
    console.log(error);
    res.send({
      exitcode: 500,
    });
  }
});

export default router;

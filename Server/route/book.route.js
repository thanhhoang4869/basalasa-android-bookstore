import bookModel from '../model/book.model.js';
import bodyParser from 'body-parser';
import express from 'express';

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }));

<<<<<<< Updated upstream
router.get('/onsale', async (req, res) => {

    try {
        const ret = await bookModel.getBookOnSale();
        console.log(ret)
        res.send({ arrBookOnSale: ret });
    } catch (err) {
        console.log(err)
        res.send({
            "exitcode": 500,
        });
    }


});
router.get('/:bookdID', async (req, res) => {
=======
router.get('/123', async (req, res) => {
  console.log(123);
});

router.get('/onsale', async (req, res) => {
  const ret = await bookModel.getBookOnSale();
  console.log(ret);
  res.send({ arrBookOnSale: ret || null });

  router.get('/', async (req, res) => {
>>>>>>> Stashed changes
    try {
      console.log('TEST');
      const ret = await bookModel.findAll();
      res.send({ arrBook: ret });
    } catch (error) {
      console.log(error);
      res.send({
        exitcode: 500,
      });
    }
  });
});
router.get('/', async (req, res) => {
    try {
        const ret = await bookModel.findAll();
        res.send({ arrBook: ret });
    } catch (error) {
        console.log(error)
        res.send({
            "exitcode": 500,
        });
    }

});

export default router;

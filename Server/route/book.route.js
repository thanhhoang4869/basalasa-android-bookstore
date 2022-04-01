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
        console.log(err)
        res.send({
            "exitcode": 500,
        });
    }

});

router.get('/onsale', async (req, res) => {
    const ret = await bookModel.getBookOnSale();
    console.log(ret);
    res.send({ arrBookOnSale: ret || null });
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

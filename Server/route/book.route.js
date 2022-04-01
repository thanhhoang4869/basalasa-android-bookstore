import bookModel from '../model/book.model.js';
import bodyParser from "body-parser";
import express from 'express'

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/onsale', async (req, res) => {
    const ret = await bookModel.getBookOnSale();
    console.log(ret)
    res.send({ arrBookOnSale: ret || null });
});

export default router
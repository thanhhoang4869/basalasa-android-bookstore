import bookModel from '../model/book.model.js';
import dotenv from 'dotenv';
import bodyParser from "body-parser";
import express from 'express'

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/', async (req, res) => {
    try {
        const ret = await categoryModel.getCategory();
        res.send(ret);
    } catch (err) {
        console.log(err)
        res.send({
            "exitcode": 500,
        });
    }
});

export default router
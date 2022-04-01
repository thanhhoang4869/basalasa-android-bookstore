import categoryModel from '../model/category.model.js';
import bodyParser from "body-parser";
import express from 'express'

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/', async (req, res) => {
    try {
        const ret = await categoryModel.getCategory();
        console.log(ret);
        res.send({ arrCategory: ret });
    } catch (err) {
        console.log(err)
        res.send({
            "exitcode": 500,
        });
    }
});

export default router
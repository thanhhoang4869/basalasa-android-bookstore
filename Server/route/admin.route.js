import accountModel from '../model/account.model.js';
import bodyParser from 'body-parser';
import express from 'express';

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }));

router.get('/getAccountList', async(req, res) => {
    console.log("admin route getacc")
    try {
        const ret = await accountModel.findAll();
        res.send({
            exitcode: 0,
            accList: ret
        });
    } catch (err) {
        console.log(err);
        res.send({
            exitcode: 500,
        });
    }
});

export default router;
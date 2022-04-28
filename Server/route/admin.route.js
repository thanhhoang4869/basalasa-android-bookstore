import accountModel from '../model/account.model.js';
import bodyParser from 'body-parser';
import express from 'express';

const router = express.Router();
router.use(bodyParser.urlencoded({ extended: false }));

router.get('/getAccountList', async(req, res) => {
    try {
        const ret = await accountModel.findAll();

        if (ret.length < 1) {
            res.send({
                exitcode: 104
            });
            return
        }

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

router.get('/getRequestList', async(req, res) => {
    try {
        const ret = await accountModel.findRequest();

        if (ret.length < 1) {
            res.send({
                exitcode: 104
            });
            return
        }

        console.log(ret)

        res.send({
            exitcode: 0,
            reqList: ret
        });
    } catch (err) {
        console.log(err);
        res.send({
            exitcode: 500,
        });
    }
});

router.post('/changeAccState', async(req, res) => {
    try {
        const email = req.body.email;
        const user = await accountModel.findByEmail(email)
        user.status = req.body.newState;

        await accountModel.updateAccount(email, user);

        res.send({
            "exitcode": 0,
            message: "Update status successfully"
        });
    } catch (err) {
        console.log(err);
        res.send({
            exitcode: 500,
        });
    }
});

router.post('/changeRole', async(req, res) => {
    try {
        const email = req.body.email;
        const user = await accountModel.findByEmail(email)
        user.role = req.body.newRole;
        user.request = 0;

        await accountModel.updateAccount(email, user);

        res.send({
            "exitcode": 0,
            message: "Update status successfully"
        });
    } catch (err) {
        console.log(err);
        res.send({
            exitcode: 500,
        });
    }
});

export default router;
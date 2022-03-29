import accountModel from '../model/account.model.js'
import bcrypt from 'bcrypt'

function assignRoutes(app) {
    app.post('/account/login', async (req, res) => {
        console.log(req.body.email)
        const account = await accountModel.findByEmail(req.body.email);
        // console.log(account[0])

        if (account === null) {
            res.send({
                "exitcode": 104
            });
            return;
        }

        const ret = bcrypt.compareSync(req.body.password, account.password);
        if (ret === false) {
            res.send({
                "exitcode": 104
            });
            return;
        }

        res.send({
            "exitcode": 0,
        });
    });
}

export default {
    assignRoutes
}
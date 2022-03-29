import accountModel from '../model/account.model.js'
import bcrypt from 'bcrypt'

function assignRoutes(app) {
    app.post('/account/login', async (req, res) => {
        const account = await accountModel.findByEmail(req.body.email);

        if (account === null) {
            res.send({
                "exitcode": 104
            });
            return;
        }

        if (account.status) {
            res.send({
                "exitcode": 708 //blocked
            });
            console.log('exit r')
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
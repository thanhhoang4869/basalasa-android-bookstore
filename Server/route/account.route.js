import accountModel from '../model/account.model.js'
import bcrypt from 'bcrypt'

function assignRoutes(app) {
    app.post('/account/login', async(req, res) => {
        console.log("Dm gi z??")
        const account = await accountModel.findByUsername(req.body.username);

        if (account === null) {
            res.send('login', {
                "exitcode": 104
            });
            return;
        }

        const ret = bcrypt.compareSync(req.body.password, user.Password);
        if (ret === false) {
            res.send('login', {});
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
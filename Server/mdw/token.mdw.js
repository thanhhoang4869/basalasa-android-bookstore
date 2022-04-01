import config from '../config/config.js'
import jwt from 'jsonwebtoken'

export default function(app) {
    app.use(function(req, res, next) {
        if (config.server.noTokenUrl.indexOf(req.url) == -1) {
            //In token url
            const token = req.headers['x-access-token']

            jwt.verify(token, config.server.secret, (err, decoded) => {
                if (err) {
                    res.status(403);
                    res.send({
                        exitcode: 2,
                        message: err
                    })
                    return
                }

                req.payload = {
                    email: decoded.email
                }
                next()
            })
        } else {
            //Non-token url
            next()
        }
    })
}
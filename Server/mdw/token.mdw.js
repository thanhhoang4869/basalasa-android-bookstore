import config from '../config/config.js'
import jwt from 'jsonwebtoken'

export default function(app) {
    app.use(function(req, res, next) {
        // console.log(req.url)
        const noToken = config.server.noTokenUrl
        for (let i = 0; i < noToken.length; i++) {
            // console.log(noToken[i])
            if (req.url.startsWith(noToken[i]) || req.url === '/') {
                next()
                return
            }
        }

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
    })
}
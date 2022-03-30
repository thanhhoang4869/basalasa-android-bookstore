import express from 'express';
const app = express()
import route from './route/route.js'
import cors from 'cors'
import db from './database/connect.js'
import logger from './utils/log.js'
import config from './config/config.js'
import jwt from 'jsonwebtoken'
//==================== Library =======================

app.use(cors())
app.use(express.json())
app.use(express.urlencoded({
    extended: true
}));

db.connect().then(() => {
    console.log("Setup db")
})

app.use(logger.logger)

//verify token if needed
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

//Bind route
route.assignRoutes(app)

//Start listen
app.listen(3000, function() {
    console.log("Begin listen on port %s...", 3000);
})
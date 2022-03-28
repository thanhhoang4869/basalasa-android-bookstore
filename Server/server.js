import express from 'express';
const app = express()
import route from './route/route.js'
import cors from 'cors'
//==================== Library =======================

app.use(cors())
app.use(express.json())
app.use(express.urlencoded({
    extended: true
}));

// db.connect().then(
//     console.log("Test db conn")
// )


// app.use(log.logger)

// app.use(function(req, res, next) {
//     if (config.server.noTokenUrl.indexOf(req.url) == -1) {
//         //In token url
//         const token = req.headers['x-access-token']

//         jwt.verify(token, config.server.secret, (err, decoded) => {
//             if (err) {
//                 res.status(403);
//                 res.send({
//                     exitcode: 2,
//                     message: err
//                 })
//                 return
//             }

//             req.payload = {
//                 username: decoded.username
//             }
//             next()
//         })
//     } else {
//         //Non-token url
//         next()
//     }
// })

// //#endregion middleware

//Bind route
route.assignRoutes(app)

//Start listen
app.listen(3000, function() {
    console.log("Begin listen on port %s...", 3000);
})
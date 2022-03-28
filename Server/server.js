import express from 'express';
const app = express()
import route from './route/route.js'
import cors from 'cors'
import db from './database/connect.js'
import logger from './log.js'
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

//Bind route
route.assignRoutes(app)

//Start listen
app.listen(3000, function() {
    console.log("Begin listen on port %s...", 3000);
})
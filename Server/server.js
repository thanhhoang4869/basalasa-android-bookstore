import express from 'express';
const app = express()
import bindRoute from './mdw/route.mdw.js'
import cors from 'cors'
import db from './database/connect.js'
import logger from './utils/log.js'
import tokenAuth from './mdw/token.mdw.js'
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
tokenAuth(app)

//Bind route
bindRoute(app);
//Start listen

app.listen(3000, function() {
    console.log("Begin listen on port %s...", 3000);
})
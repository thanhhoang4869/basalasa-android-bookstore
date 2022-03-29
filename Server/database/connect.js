import mongoose from 'mongoose'
// import config from '../config/config.js'
// const url = config.url

import dotenv from 'dotenv'
dotenv.config()

function connect() {
    return new Promise((resolve, reject) => {
        mongoose.connect(process.env.DB_URL, {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        }).then(() => {
            const conn = mongoose.connection;
            console.log(conn.name);
            resolve()
            return;
        }).catch(err => {
            console.log(`Error creating MongoDB connection ${err}`);
            reject();
        })
    })
}

export default {
    connect
}
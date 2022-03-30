import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

function connect() {
    return new Promise((resolve, reject) => {
        mongoose.connect(url, {
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
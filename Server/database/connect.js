const mongoose = require('mongoose')
const config = require('../config/config')
const url = config.database.url

exports.connect = function connect() {
    return new Promise((resolve, reject) => {
        mongoose.connect(url, {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        }).then(() => {
            conn = mongoose.connection;
            console.log(conn.name);
            resolve()
            return;
        }).catch(err => {
            console.log(`Error creating MongoDB connection ${err}`);
            reject();
        })
    })
}
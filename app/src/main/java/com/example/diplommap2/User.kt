package com.example.diplommap2

class User {
    lateinit var id: String
    lateinit var name: String
    lateinit var secname: String
    lateinit var mail: String
    lateinit var pass: String

    constructor(id: String, name: String, secname: String, mail: String, pass: String) {
        this.id = id
        this.name = name
        this.secname = secname
        this.mail = mail
        this.pass = pass
    }
}
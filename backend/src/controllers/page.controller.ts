import * as express from "express"
import Page from "../models/page"
const bcrypt = require('bcrypt');

export class PageController {
    create_page = async (req: express.Request, res: express.Response) => {
        const page = new Page({
            name: req.body.name,
            headline: req.body.headline,
            about: req.body.about,
            profile_image: req.body.profile_image,
            industry: req.body.industry,
            website: req.body.website,
            location: req.body.location,
            admin: req.body.admin,
            date_created: Date.now()
        });

        page.save().then(page => {
            res.json('success')
        })
    }

    get_page_by_name = async (req: express.Request, res: express.Response) => {
        const name = req.params.name;

        const page = await Page.findOne({
            'name': name
        })

        res.json(page)
    }
}
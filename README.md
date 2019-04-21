# jeff-farm
a web server/site to track the farm

# Deployment
* Deployed on heroku at jeff-farm.herokuapp.com
* The ui (jeff-farm-ui) and webservice (jeff-farm-ws) are actually deployed on different Heroku apps.  The webservice is deployed at jeff-farm-ws.herokuapp.com.
* Because the ui and webservice are in one repository, git-subtree must be used to deploy the apps separately:
`git subtree push --prefix jeff-farm-ws/ heroku-backend master` and `git subtree push --prefix jeff-farm-ui/ heroku master`

* The heroku Postgresql addon is used to run liquibase to set up and apply changes to the database.  It was added to jeff-farm-ws with `heroku addons:create heroku-postgresql`.

## License
Project is licensed under the [MIT license](LICENSE.md).

App for managing tasks and their statuses, alongside robot states.

`bwr` and `bwr-audit` apps have Flyway migration tool which creates necessary tables upon start.
Important thing to mention is that before starting all apps, you should change application.yml to match your local DB config.
Scripts are inserting a test robot and test user with `id: 1`. `bwr-robot` app has that id set in the application.yml file as a property value.

There are 4 endpoints:
1. Upload task
2. Start
3. Stop
4. End task

- *Upload task* does the job of creating a new task in the DB
- *Start* does the job of starting the specified task on a specified robot. If the robot is in OFF state, app first sends the TURN_ON_ROBOT command to the robot, which sets the robot state to ON once robot acknowledges the message. After that, or if robot was initially in ON state, app proceeds with START_COMMAND
- *Stop* does the job of stopping the specified task on a specified robot
- *End task* does the job of stopping the specified task on a specified robot

Used `Quartz` scheduler for KEEP_ALIVE logic. If the robot is in ON state, `bwr-robot` app sends the KEEP_ALIVE message to `bwr` app.
When the first message is consumed, `bwr` app creates a job that will update the robot state to OFF after 10 seconds if no more KEEP_ALIVE messages are produced.
After that, on each new KEEP_ALIVE message, app just reschedules the job for additional 10s period.
Once `bwr-robot` app is turned off and no more KEEP_ALIVE messages are sent, job will be executed after 10 seconds.

All actions are audited in `bwr-audit`.`audit_log` table.
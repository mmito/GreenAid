# Warning Suppression Reasons

## Week 4 Checkstyle Version 2 Suppression Reasons
To connect the database to the server, we need to use the exact same variable names in database tables and Java class fields. Database only allows, for example, last_update but checkstyle enforces the lastUpdate notation. That's why we suppressed variable name declaration warning of Checkstyle.
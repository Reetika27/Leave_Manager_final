CREATE TABLE securityquestion(
securityQuestionId integer NOT NULL,
question TEXT NOT NULL,
lastAccessUser TEXT,
lastUpdateTime time without time zone,
CONSTRAINT securityquestion_pkey PRIMARY KEY (securityQuestionId),
CONSTRAINT questions_unique UNIQUE (question)
);

CREATE TABLE leaveinfo(
designationId integer NOT NULL DEFAULT 0,
designation TEXT NOT NULL DEFAULT 0,
totalPermissibleLeaves integer NOT NULL,
lastAccessUser TEXT,
lastUpdateTime time without time zone,
CONSTRAINT leaveinfo_pkey PRIMARY KEY (designationId)
);

CREATE TABLE leave(
leaveId integer NOT NULL,
userId integer NOT NULL,
startDate date NOT NULL,
endDate date NOT NULL,
reason TEXT,
status TEXT NOT NULL,
lastAccessUser TEXT,
lastUpdateTime time without time zone,
CONSTRAINT leave_pkey PRIMARY KEY (leaveId),
CONSTRAINT "userId_fk" FOREIGN KEY (userid)
        REFERENCES users (userid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE users(
userId integer NOT NULL,
userName TEXT NOT NULL,
email character(50) NOT NULL,
password varchar(200) NOT NULL,
role TEXT NOT NULL,
securityQuestionId integer,
securityQuestionAnswer TEXT,
designationId integer NOT NULL DEFAULT 0,
lastAccessUser TEXT,
lastUpdateTime time without time zone,
CONSTRAINT users_pkey PRIMARY KEY (userId),
CONSTRAINT "uniqueCredentials" UNIQUE (email, password),
CONSTRAINT users_username_email_key UNIQUE (username, email),
CONSTRAINT users_username_password_key UNIQUE (username, password),
CONSTRAINT "designationId_fk" FOREIGN KEY (designationid)
	REFERENCES leaveinfo (designationid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE SET DEFAULT,
CONSTRAINT "securityQuestionId_fk" FOREIGN KEY (securityquestionid)
        REFERENCES securityquestion (securityquestionid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE SET DEFAULT
);

CREATE SEQUENCE user_pk start 1;
CREATE SEQUENCE designation_pk start 1;
CREATE SEQUENCE leave_pk start 1;
CREATE SEQUENCE question_pk start 1;


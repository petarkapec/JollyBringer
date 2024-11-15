CREATE TABLE ROLE
(
  role_id LONG NOT NULL,
  role_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE PARTICIPANT
(
  id LONG NOT NULL,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  role_id LONG NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (role_id) REFERENCES ROLE(role_id),
  UNIQUE (email)
);

CREATE TABLE GROUP
(
  group_id LONG NOT NULL,
  group_name VARCHAR(64) NOT NULL,
  id LONG NOT NULL,
  PRIMARY KEY (group_id),
  FOREIGN KEY (id) REFERENCES PARTICIPANT(id),
  UNIQUE (group_name)
);

CREATE TABLE ACTIVITY
(
  activity_id LONG NOT NULL,
  activity_name VARCHAR(255) NOT NULL,
  ending_date DATE NOT NULL,
  activity_status VARCHAR(32) NOT NULL,
  creation_type VARCHAR(32) NOT NULL,
  id LONG,
  group_id LONG NOT NULL,
  PRIMARY KEY (activity_id),
  FOREIGN KEY (id) REFERENCES PARTICIPANT(id),
  FOREIGN KEY (group_id) REFERENCES GROUP(group_id)
);

CREATE TABLE MESSAGE
(
  message_id LONG NOT NULL,
  content VARCHAR(255) NOT NULL,
  sent_time DATE NOT NULL,
  id LONG NOT NULL,
  group_id LONG NOT NULL,
  PRIMARY KEY (message_id),
  FOREIGN KEY (id) REFERENCES PARTICIPANT(id),
  FOREIGN KEY (group_id) REFERENCES GROUP(group_id)
);

CREATE TABLE FEEDBACK
(
  feedback_id LONG NOT NULL,
  isLiked INT NOT NULL,
  comment VARCHAR(255) NOT NULL,
  id LONG NOT NULL,
  activity_id LONG NOT NULL,
  PRIMARY KEY (feedback_id),
  FOREIGN KEY (id) REFERENCES PARTICIPANT(id),
  FOREIGN KEY (activity_id) REFERENCES ACTIVITY(activity_id)
);

CREATE TABLE APPLICATION_REQUEST
(
  id LONG NOT NULL,
  isApplied BOOLEAN NOT NULL,
  user_id LONG NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES PARTICIPANT(id)
);

CREATE TABLE inGroup
(
  id LONG NOT NULL,
  group_id LONG NOT NULL,
  PRIMARY KEY (id, group_id),
  FOREIGN KEY (id) REFERENCES PARTICIPANT(id),
  FOREIGN KEY (group_id) REFERENCES GROUP(group_id)
);

CREATE TABLE activity_worker
(
  activity_id LONG NOT NULL,
  id LONG NOT NULL,
  PRIMARY KEY (activity_id, id),
  FOREIGN KEY (activity_id) REFERENCES ACTIVITY(activity_id),
  FOREIGN KEY (id) REFERENCES PARTICIPANT(id)
);

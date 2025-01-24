CREATE TABLE ROLE
(
  role_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE PARTICIPANT
(
  participant_id INT NOT NULL,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (participant_id),
  FOREIGN KEY (role_id) REFERENCES ROLE(role_id),
  UNIQUE (email)
);

CREATE TABLE PARTICIPANT_GROUP
(
  group_id INT NOT NULL,
  name VARCHAR(20) NOT NULL,
  president INT NOT NULL,
  PRIMARY KEY (group_id),
  FOREIGN KEY (president) REFERENCES PARTICIPANT(participant_id),
  UNIQUE (name)
);

CREATE TABLE ACTIVITY
(
  activity_id INT NOT NULL,
  activity_name VARCHAR(255) NOT NULL,
  date VARCHAR(255) NOT NULL,
  activity_status VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  created_by INT,
  group_id INT NOT NULL,
  PRIMARY KEY (activity_id),
  FOREIGN KEY (created_by) REFERENCES PARTICIPANT(participant_id),
  FOREIGN KEY (group_id) REFERENCES PARTICIPANT_GROUP(group_id)
);

CREATE TABLE CHATMESSAGE
(
  message_id INT NOT NULL,
  content VARCHAR(255) NOT NULL,
  timestamp VARCHAR(255) NOT NULL,
  participant_id INT NOT NULL,
  PRIMARY KEY (message_id),
  FOREIGN KEY (participant_id) REFERENCES PARTICIPANT(participant_id)
);

CREATE TABLE FEEDBACK
(
  feedback_id INT NOT NULL,
  is_liked VARCHAR(255) NOT NULL,
  comment VARCHAR(255) NOT NULL,
  participant_id INT NOT NULL,
  activity_id INT NOT NULL,
  PRIMARY KEY (feedback_id),
  FOREIGN KEY (participant_id) REFERENCES PARTICIPANT(participant_id),
  FOREIGN KEY (activity_id) REFERENCES ACTIVITY(activity_id)
);

CREATE TABLE APPLICATION_REQUEST
(
  application_id INT NOT NULL,
  is_applied INT NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (application_id),
  FOREIGN KEY (user_id) REFERENCES PARTICIPANT(participant_id)
);

CREATE TABLE participant_group_members
(
  members_id INT NOT NULL,
  participant_group_id INT NOT NULL,
  PRIMARY KEY (members_id, participant_group_id),
  FOREIGN KEY (members_id) REFERENCES PARTICIPANT(participant_id),
  FOREIGN KEY (participant_group_id) REFERENCES PARTICIPANT_GROUP(group_id)
);

CREATE TABLE participant_group_messages
(
  message_id INT NOT NULL,
  participant_group_id INT NOT NULL,
  PRIMARY KEY (message_id, participant_group_id),
  FOREIGN KEY (message_id) REFERENCES CHATMESSAGE(message_id),
  FOREIGN KEY (participant_group_id) REFERENCES PARTICIPANT_GROUP(group_id)
);
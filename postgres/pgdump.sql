CREATE TABLE "GROUP"
(
    group_id INT NOT NULL,
    group_name VARCHAR(64) NOT NULL,
    id_organizator INT NOT NULL,
    PRIMARY KEY (group_id)
);

CREATE TABLE "ROLE"
(
    role_id INT NOT NULL,
    role_name VARCHAR(32) NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE "USER"
(
    user_id INT NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    provider VARCHAR(100),
    role_id INT NOT NULL,
    PRIMARY KEY (user_id),
    FOREIGN KEY (role_id) REFERENCES "ROLE"(role_id)
);

CREATE TABLE "ACTIVITY"
(
    activity_id INT NOT NULL,
    activity_name VARCHAR(128) NOT NULL,
    ending_date DATE NOT NULL,
    activity_status VARCHAR(32) NOT NULL,
    creation_type VARCHAR(32) NOT NULL,
    user_id INT,
    group_id INT NOT NULL,
    PRIMARY KEY (activity_id),
    FOREIGN KEY (user_id) REFERENCES "USER"(user_id),
    FOREIGN KEY (group_id) REFERENCES "GROUP"(group_id)
);

CREATE TABLE "MESSAGE"
(
    message_id INT NOT NULL,
    content VARCHAR(256) NOT NULL,
    sent_time DATE NOT NULL,
    user_id INT NOT NULL,
    group_id INT NOT NULL,
    PRIMARY KEY (message_id),
    FOREIGN KEY (user_id) REFERENCES "USER"(user_id),
    FOREIGN KEY (group_id) REFERENCES "GROUP"(group_id)
);

CREATE TABLE "FEEDBACK"
(
    feedback_id INT NOT NULL,
    isLiked INT NOT NULL,
    comment VARCHAR(256) NOT NULL,
    user_id INT NOT NULL,
    activity_id INT NOT NULL,
    PRIMARY KEY (feedback_id),
    FOREIGN KEY (user_id) REFERENCES "USER"(user_id),
    FOREIGN KEY (activity_id) REFERENCES "ACTIVITY"(activity_id)
);

CREATE TABLE "inGroup"
(
    user_id INT NOT NULL,
    group_id INT NOT NULL,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES "USER"(user_id),
    FOREIGN KEY (group_id) REFERENCES "GROUP"(group_id)
);

CREATE TABLE "activity_worker"
(
    activity_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (activity_id, user_id),
    FOREIGN KEY (activity_id) REFERENCES "ACTIVITY"(activity_id),
    FOREIGN KEY (user_id) REFERENCES "USER"(user_id)
);

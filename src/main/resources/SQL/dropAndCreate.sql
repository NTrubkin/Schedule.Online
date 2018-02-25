DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE groups
(
  id        SERIAL      NOT NULL
    CONSTRAINT groups_pkey
    PRIMARY KEY,
  name      VARCHAR(40) NOT NULL,
  leader_id INTEGER     NOT NULL
);

CREATE TABLE accounts
(
  id                    SERIAL      NOT NULL
    CONSTRAINT accounts_pkey
    PRIMARY KEY,
  first_name            VARCHAR(40) NOT NULL,
  second_name           VARCHAR(40) NOT NULL,
  email                 VARCHAR(240),
  -- todo подумать о способе хранения номера телефона
  phone_number          BIGINT,
  passhash              VARCHAR(40) NOT NULL,
  group_id              INTEGER,
  settings_notification BOOLEAN     NOT NULL,
  schedule_notidication BOOLEAN     NOT NULL,
  facebook_id           BIGINT,
  google_id             BIGINT,
  vk_id                 BIGINT,
  CONSTRAINT accounts_groups_id_fk FOREIGN KEY (group_id)
  REFERENCES groups (id)
  ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE unverified_accounts
(
  id                SERIAL      NOT NULL
    CONSTRAINT unverified_accounts_pkey
    PRIMARY KEY,
  first_name        VARCHAR(40) NOT NULL,
  second_name       VARCHAR(40) NOT NULL,
  email             VARCHAR(240),
  phone_number      BIGINT,
  name              VARCHAR(40) NOT NULL,
  passhash          VARCHAR(40) NOT NULL,
  verification_code INT         NOT NULL
);

ALTER TABLE public.groups
  ADD CONSTRAINT groups_accounts_id_fk
FOREIGN KEY (leader_id) REFERENCES accounts (id) ON UPDATE CASCADE;

CREATE TABLE public.lessons
(
  id             SERIAL PRIMARY KEY NOT NULL,
  name           VARCHAR(40)        NOT NULL,
  room           INT,
  start_datetime TIMESTAMP          NOT NULL,
  end_datetime   TIMESTAMP          NOT NULL,
  teacher        VARCHAR(40),
  group_id       INT                NOT NULL,
  CONSTRAINT lessons_groups_id_fk FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.events
(
  id             SERIAL PRIMARY KEY NOT NULL,
  name           VARCHAR(40)        NOT NULL,
  start_datetime TIMESTAMP          NOT NULL,
  place          VARCHAR(40)        NOT NULL,
  group_id       INT                NOT NULL,
  description    VARCHAR(1000)      NOT NULL
);

CREATE TABLE public.tags
(
  id   SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(40)        NOT NULL
);

CREATE TABLE public.lesson_tags
(
  lesson_id INT NOT NULL,
  tag_id    INT NOT NULL,
  PRIMARY KEY (lesson_id, tag_id),
  CONSTRAINT lesson_tags_lessons_id_fk FOREIGN KEY (lesson_id) REFERENCES lessons (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT lesson_tags_tags_id_fk FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.event_tags
(
  event_id INT NOT NULL,
  tag_id   INT NOT NULL,
  PRIMARY KEY (event_id, tag_id),
  CONSTRAINT event_tags_event_id_fk FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT event_tags_tags_id_fk FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.permissions
(
  id           SERIAL PRIMARY KEY NOT NULL,
  account_id   INT                NOT NULL,
  group_id     INT                NOT NULL,
  admin        BOOLEAN            NOT NULL,
  lessons_edit BOOLEAN            NOT NULL,
  events_edit  BOOLEAN            NOT NULL,
  CONSTRAINT permissions_account_id_fk FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT permissions_group_id_fk FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE ON UPDATE CASCADE
);


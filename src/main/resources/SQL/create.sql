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
  id       SERIAL      NOT NULL
    CONSTRAINT accounts_pkey
    PRIMARY KEY,
  firstName     VARCHAR(40) NOT NULL,
  secondName     VARCHAR(40) NOT NULL,
  email VARCHAR(240),
  -- todo подумать о способе хранения номера телефона
  phoneNumber BIGINT,
  name     VARCHAR(40) NOT NULL,
  passhash VARCHAR(40) NOT NULL,
  group_id INTEGER
    CONSTRAINT accounts_groups_id_fk
    REFERENCES groups
    ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE unverified_accounts
(
  id       SERIAL      NOT NULL
    CONSTRAINT accounts_pkey
    PRIMARY KEY,
  firstName     VARCHAR(40) NOT NULL,
  secondName     VARCHAR(40) NOT NULL,
  email VARCHAR(240),
  phoneNumber BIGINT,
  name     VARCHAR(40) NOT NULL,
  passhash VARCHAR(40) NOT NULL,
  verification_code INT NOT NULL
);

ALTER TABLE public.groups
  ADD CONSTRAINT groups_accounts_id_fk
FOREIGN KEY (leader_id) REFERENCES accounts (id) ON UPDATE CASCADE;

CREATE TABLE public.lessons
(
  id       SERIAL PRIMARY KEY NOT NULL,
  name     VARCHAR(40)        NOT NULL,
  room     INT,
  dateTime TIMESTAMP          NOT NULL,
  teacher  VARCHAR(40),
  group_id INT                NOT NULL,
  CONSTRAINT lessons_groups_id_fk FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.semester_starts
(
  group_id INT       NOT NULL,
  date     TIMESTAMP NOT NULL
);

CREATE TABLE public.events
(
  id          SERIAL PRIMARY KEY NOT NULL,
  name        VARCHAR(40)        NOT NULL,
  dateTime    TIMESTAMP          NOT NULL,
  description VARCHAR(1000)      NOT NULL
);
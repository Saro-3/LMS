CREATE DATABASE lms_db;

use lms_db;

create table roles(
	id bigint auto_increment primary key,
    name varchar(50) not null unique
);

insert into roles (name) values
('ROLE_ADMIN'),
('ROLE_INSTRUCTOR'),
('ROLE_STUDENT');

select * from roles;

create table users(
	id bigint auto_increment primary key,
    name varchar(100) not null,
    email varchar(150) not null unique,
    password varchar(255) not null,
    profile_image varchar(500),
    bio TEXT,
    role_id bigint not null,
    is_active boolean default true,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    constraint fk_users_role
		foreign key (role_id) references roles(id)
);

describe users;

describe roles;

create table categories (
	id bigint auto_increment primary key,
	name varchar(100) not null unique,
    description TEXT,
    image_url varchar(500),
    is_active boolean default true,
    created_at timestamp default current_timestamp
);

insert into categories (name, description) values
('Programming', 'Programming and software development courses'),
('Web Development', 'Frontend and backend web development'),
('Database', 'Database management and SQL'),
('Cloud Computing', 'Cloud and DevOps technologies'),
('Artificial Intelligence', 'AI and machine learning courses');

select * from categories;

create table courses (
	id bigint auto_increment primary key,
    title varchar(200) not null,
    description TEXT not null,
    thumbnail_url varchar(500),
    
    instructor_id bigint not null,
    category_id bigint not null,
    
    level varchar(50),
    duration_hours decimal(5, 2),
    
    status ENUM(
		'DRAFT',
        'PENDING_APPROVAL',
        'PUBLISHED',
        'REJECTED'
    ) default 'DRAFT',
    
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    
    constraint fk_courses_instructor
		foreign key (instructor_id) references users(id),
        
	constraint fk_courses_category
		foreign key (category_id) references categories(id)
);

show tables;

create table course_modules(
	id bigint auto_increment primary key,
    course_id bigint not null,
    title varchar(200) not null,
    description TEXT,
    
    module_order int not null,
    
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    
    constraint fk_modules_course
		foreign key (course_id) references courses(id) on delete cascade
); 

create table lessons (
	id bigint auto_increment primary key,
    
    module_id bigint not null,
    
    title varchar(200) not null,
    description TEXT,
    
    lesson_type ENUM(
		'VIDEO',
        'TEXT',
        'DOCUMENT'
	) default 'VIDEO',
    
    video_url varchar(500),
    content TEXT,
    
    lesson_order int not null,
    
    duration_minutes int,
    
    is_preview boolean default false,
    
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    
    constraint fk_lessons_module
		foreign key (module_id) references course_modules(id) on delete cascade
);

show tables;

create table learning_materials (
	id bigint auto_increment primary key,
    lesson_id bigint not null,
    title varchar(200) not null,
    description TEXT,
    
    material_type ENUM(
		'PDF',
        'DOCUMENT',
        'LINK',
        'CODE',
        'OTHER'
    ) default 'DOCUMENT',
    
    file_url varchar(500),
    external_url varchar(500),
    
    file_size bigint,
    
    created_at timestamp default current_timestamp,
    
    constraint fk_materials_lesson
		foreign key (lesson_id) references lessons(id) on delete cascade
);

show tables;

create table enrollments (
	id bigint auto_increment primary key,
    
    student_id bigint not null,
    course_id bigint not null,
    
    enrollment_date timestamp default current_timestamp,
    
    status ENUM(
		'ACTIVE',
        'COMPLETED',
        'CANCELLED'
    ) default 'ACTIVE',
    
    completed_at timestamp null,
    
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    
    constraint fk_enrollments_student
		foreign key (student_id) references users(id) on delete cascade,
        
	constraint fk_enrollments_course
		foreign key (course_id) references courses(id) on delete cascade,
	
    constraint uk_student_course
		unique (student_id, course_id)
);

show tables;

create table lesson_progress (
	id bigint auto_increment primary key,
    
    enrollment_id bigint not null,
    lesson_id bigint not null,
    
    is_completed boolean default false,
    
    started_at timestamp null,
    completed_at timestamp null,
    
    last_accessed_at timestamp null,
    
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    
    constraint fk_progress_enrollment
		foreign key (enrollment_id) references enrollments(id) on delete cascade,
        
	constraint uk_enrollment_lesson
		unique (enrollment_id, lesson_id)
);

show tables;

describe enrollments;

describe lesson_progress;

create table quizzes (
	id bigint auto_increment primary key,
    
    course_id bigint not null,
    
    title varchar(200) not null,
    description TEXT,
    
    passing_score decimal(5, 2) default 50.00,
    time_limit_minutes int,
    
    is_published boolean default false,
    
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    
    constraint fk_quizzes_course
		foreign key (course_id) references courses(id) on delete cascade
);

create table quiz_questions (
	id bigint auto_increment primary key,
    
    quiz_id bigint not null,
    
    question_text TEXT not null,
    
    option_a varchar(500) not null,
    option_b varchar(500) not null,
    option_c varchar(500) not null,
    option_d varchar(500) not null,
    
    correct_option ENUM(
		'A',
        'B',
        'C',
        'D'
    ) not null,
    
    marks decimal(5, 2) default 1.00,
    
    question_order int not null,
    
    created_at timestamp default current_timestamp,
    
    constraint fk_questions_quiz
		foreign key (quiz_id) references quizzes(id) on delete cascade
);

create table quiz_attempts (
	id bigint auto_increment primary key,
    
    quiz_id bigint not null,
    student_id bigint not null,
    
    score decimal(5, 2) not null default 0.00,
    
    total_marks decimal(5, 2) not null default 0.00,
    
    percentage decimal(5, 2) not null default 0.00,
    
    passed boolean default false,
    
    started_at timestamp default current_timestamp,
    submitted_at timestamp null,
    
    created_at timestamp default current_timestamp,
    
    constraint fk_attempts_quiz
		foreign key (quiz_id) references quizzes(id) on delete cascade,
        
	constraint fk_attempts_student
		foreign key (student_id) references users(id) on delete cascade
);

create table assignments (
	id bigint auto_increment primary key,
    
    course_id bigint not null,
    
    title varchar(200) not null,
    description TEXT,
    
    max_marks decimal(5, 2) default 100.00,
    
    due_date datetime,
    
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
		on update current_timestamp,
        
	constraint fk_assignments_course
		foreign key (course_id) references courses(id) on delete cascade
);

create table assignment_submissions (
	id bigint auto_increment primary key,
    
    assignment_id bigint not null,
    student_id bigint not null,
    
    submission_text TEXT,
    file_url varchar(500),
    
    narks decimal(5, 2),
    
    instructor_feedback TEXT,
    
    status ENUM(
		'SUBMITTED',
        'EVALUATED',
        'RETURNED'
    )	default 'SUBMITTED',
    
    submitted_at timestamp default current_timestamp,
    evaluated_at timestamp null,
    
    constraint fk_submissions_assignment
		foreign key (assignment_id) references assignments(id) on delete cascade,
        
	constraint fk_submission_student
		foreign key (student_id) references users(id) on delete cascade
);

show tables;

create table certificates (
	id bigint auto_increment primary key,
    
    certificate_id varchar(100) not null unique,
    
    student_id bigint not null,
    course_id bigint not null,
    enrollment_id bigint not null,
    
    issued_at timestamp default current_timestamp,
    
    certificate_url varchar(500),
    
    created_at timestamp default current_timestamp,
    
    constraint fk_certificates_student
		foreign key (student_id) references users(id) on delete cascade,
        
	constraint fk_certificates_course
		foreign key (course_id) references courses(id) on delete cascade,
        
	constraint fk_certificates_enrollment
		foreign key (enrollment_id) references enrollments(id) on delete cascade,
        
	constraint uk_certificate_enrollment
		unique (enrollment_id)
);

show tables;

select * from users;

select * from courses;

use lms_db;

describe  courses;

select * from lessons;

describe learning_materials;

select * from enrollments;

SELECT
    u.id,
    u.name,
    u.email,
    u.is_active,
    u.role_id,
    r.name AS role_name
FROM users u
JOIN roles r ON u.role_id = r.id
WHERE u.email = 'saravanan@example.com';

describe lesson_progress;

SHOW CREATE TABLE lesson_progress;



SELECT *
FROM lessons;

DESCRIBE quizzes;

DESCRIBE quiz_questions;

DESCRIBE quiz_attempts;

describe assignment_submissions;

describe assignments;

alter table assignment_submissions rename column narks to marks;

DESCRIBE certificates;

SELECT *
FROM enrollments
WHERE id = 1;

UPDATE enrollments
SET status = 'COMPLETED',
    completed_at = CURRENT_TIMESTAMP
WHERE id = 1;

SELECT id, student_id, course_id, status, completed_at
FROM enrollments
WHERE id = 1;

SELECT *
FROM certificates;

SELECT id, title, max_marks
FROM assignments
WHERE id = 1;

UPDATE assignments
SET max_marks = 100
WHERE id = 1;
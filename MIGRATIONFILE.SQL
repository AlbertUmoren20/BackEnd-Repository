-- V1__change_matric_number_to_varchar.sql
ALTER TABLE students 
ALTER COLUMN matricnumber TYPE VARCHAR(20);
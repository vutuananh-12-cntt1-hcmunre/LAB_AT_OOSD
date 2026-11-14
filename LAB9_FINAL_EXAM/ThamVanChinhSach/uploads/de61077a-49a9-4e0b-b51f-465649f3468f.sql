create view high_gpa_students as
select name,major,gpa
from students
where gpa>=8

select * from high_gpa_students


create view students_major_info as
select name,major_name,gpa 
from students_join join majors on students_join.major_id=majors.id

select * from students_major_info
where gpa>=8


create view major_statistics as
select major_name, count(*) as so_sv, avg(gpa) as gpa_tb, max(gpa) as gpa_max
from students_join join majors on students_join.major_id=majors.id
group by major_name


select * from major_statistics
where gpa_tb>=8
order by gpa_tb desc
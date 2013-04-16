-- Oppgave 5

-- a)
select 
	sno, 
	sname
from supplier
where status > 15;

-- b)
select s.sname, s.city from supplier s
left join SuppliesPart sp on s.sno = sp.sno
left join Part p on sp.pno = p.pno
where p.pname like 'Screw';

-- c)
select p.pno, p.pname, count(*) as 'ant' from part
left join SuppliesPart sp on p.pno = sp.pno
group by p.pno
having ant>1;
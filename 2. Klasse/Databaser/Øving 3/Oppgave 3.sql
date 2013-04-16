-- 	Oppgave 3

-- a)
select tittel from bok;

-- b)
select * from forfatter where nasjoforfatternalitet like "Norsk";

-- c)
select forlagnavn, telefon from forlag where adresse like "Oslo";

-- e)
SELECT b.tittel, b.utgittår 
FROM forfatter f
LEFT JOIN bokforfatter bf ON bf.forfatterid = f.forfatterid
LEFT JOIN bok b ON bf.bokid = b.bokid
WHERE 
	fornavn like "Knut" and 
	etternavn like "hamsun"
;

-- f)
select fornavn, etternavn, fødeår
from forfatter
where etternavn like 'H%'
;

-- g)
select count(*) from forlag;

-- i)
select 
	f.fornavn, 
	f.etternavn, 
	count(*) as 'antall'
from forfatter f
left join bokforfatter bf on f.forfatterid = bf.forfatterid
left join bok b on b.bokid = bf.bokid
group by f.fornavn, f.etternavn
order by antall desc;

-- j)
select tittel, utgittår from bok order by utgittår asc limit 1;

-- k)
select f.forlagnavn, count(*) as 'antall' from forlag f 
left join bok b on f.forlagid = b.forlagid
group by f.forlagid
having antall > 2
;
-- l)
select forlagnavn from forlag f
left join bok b on b.forlagid = f.forlagid
where b.forlagid IS NULL;


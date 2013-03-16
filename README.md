Kasetsart University Grade Notification (Command Line Interface)
====================

### จุดประสงค์ : 

ใช้โปรแกรมแจ้งเตือน ผลเกรด ที่ออกมาใหม่ โดยไม่ต้องเข้าเว็บ ดูเกรดของ ม.เกษตร ลดการเข้าดูเว็บด้วยตัวเองบ่อยๆจนเกินไป จนไม่ได้ทำอะไร -w-

### ความต้องการขั้นต่ำ : 

Java 6 หรือ Java 7

ติดตั้งจาก [Download](http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html)

Progarm Version Log :
--------------------

### Version Beta2 [16/03/2013] [Download](https://dl.dropbox.com/u/24254026/ku-grade/Jar/KUGradeNotify-Beta2.zip)
- เพื่มการรองรับ การเลือกหลายวิทยาเขต (บางเขน, กำแพงแสน)
- ปรับรูปแบบการแสดงผลใหม่

### Version Beta1 [28/12/2012] [Download](https://dl.dropbox.com/u/24254026/ku-grade/Jar/KUGradeNotify-Beta1.zip)
- โปรแกรมถูกปล่อยครั้งแรก

วิธีใช้งาน :
--------------------

1.) [Download](https://dl.dropbox.com/u/24254026/ku-grade/Jar/KUGradeNotify-Beta2.zip) ก่อนเลย แล้วแตกไฟล์

2.) ถ้าใช้ mac หรือ linux ให้ เปิด Terminal แล้ว cd (เปลี่ยน folder) ไปที่ folder ของโปรแกรม แล้วพิมพ์
	
	./gku-mac

ถ้าใช้ Windows ให้เปิดจาก gku-win-java6.bat / gku-win-java7.bat ตาม Version ของ Java ในเครื่อง

3.) เข้ามาในโปรแกรมแล้วจะมีให้เลือก 2 โหมด 1.) แจ้งผลเกรด เมื่อเกรดตัวใหม่ออก [พิมพ์ S หรือ s] 2.) แจ้งเมื่อเกรดตัวใหม่ออกมาอย่างเดียว ไม่แจ้งผล [พิมพ์ N หรือ n]

4.) ใส่ Username / Password / วิทยาเขต

5.) โปรแกรมจะโชว์ผลเกรดล่าสุดออกมา แล้วโปรแกรมจะทำการดึงข้อมูล มาทุกๆ 30 วินาที มาตรวจสอบ [สามารถเปิดทิ้งไว้ตลอดเวลาได้]

![1](https://dl.dropbox.com/u/24254026/ku-grade/pic/1.png)

6.) เมื่อเกรดใหม่ออกมา จะแจ้งเตือนด้วย เสียงประมาณ 6 วินาที แล้วจะแสดงผลออกมา จากนั้นโปรแกรมจะหยุดทำงาน

![2](https://dl.dropbox.com/u/24254026/ku-grade/pic/2.png)

ถ้าหากรันไม่ได้ ให้เปืด Command Line/Terminal แล้ว cd (เปลี่ยน folder) ไปโฟรเดอร์ของโปรแกรม

	java -jar KUGradeNotify-Beta1.jar

Know Bug Issue
--------------------
- Windows จะมีปัญหาเรื่องการแสดงผลภาษาไทย ใน command line (แก้ไม่ได้ นอกจากทำ version GUI)
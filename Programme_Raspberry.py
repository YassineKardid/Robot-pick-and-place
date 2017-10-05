from socket import *
import RPi.GPIO as GPIO
import time
import signal, os
import random
import sys
from threading import Thread
import time


GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)

def handler(signum , frame):
        conn.close()
        s.close()
        print "handler......"

#Droite : Moteur1 et Moteur3
#Gauche : Moteur2 et Moteur4


isRunning = False

Moteur1A = 11
Moteur1B = 12
Moteur1E = 13

Moteur2A = 15
Moteur2B = 16
Moteur2E = 18

Moteur3A = 36
Moteur3B = 38
Moteur3E = 40

Moteur4A = 35
Moteur4B = 37
Moteur4E = 33

VitesseMotor1 = 100;
VitesseMotor2 = 100;
VitesseMotor3 = 100;
VitesseMotor4 = 100;
VitesseRotationMax = 100;
VitesseRotationMin = 5;


GPIO.setup(Moteur1A , GPIO.OUT)
GPIO.setup(Moteur1B , GPIO.OUT)
GPIO.setup(Moteur1E , GPIO.OUT)

GPIO.setup(Moteur2A , GPIO.OUT)
GPIO.setup(Moteur2B , GPIO.OUT)
GPIO.setup(Moteur2E , GPIO.OUT)

GPIO.setup(Moteur3A , GPIO.OUT)
GPIO.setup(Moteur3B , GPIO.OUT)
GPIO.setup(Moteur3E , GPIO.OUT)

GPIO.setup(Moteur4A , GPIO.OUT)
GPIO.setup(Moteur4B , GPIO.OUT)
GPIO.setup(Moteur4E , GPIO.OUT)

pwm1 = GPIO.PWM(Moteur1E , 50)
pwm1.start(100);

pwm2 = GPIO.PWM(Moteur2E , 50)
pwm2.start(100);

pwm3 = GPIO.PWM(Moteur3E , 50)
pwm3.start(100);

pwm4 = GPIO.PWM(Moteur4E , 50)
pwm4.start(100);



TRIG = 29                                  #Associate pin 29 to TRIG
ECHO = 31                                  #Associate pin 31 to ECHO

print "Distance measurement in progress"

GPIO.setup(TRIG,GPIO.OUT)                  #Set pin as GPIO out
GPIO.setup(ECHO,GPIO.IN)                   #Set pin as GPIO in


class MesureDistance(Thread):

        
        def __init__(self):
                Thread.__init__(self)

        def run(self):
                global isRunning
                while True :
                        isObstacle = False
                        GPIO.output(TRIG, False)                 #Set TRIG as LOW
                        print "Waitng For Sensor To Settle"
                        time.sleep(2)                            #Delay of 2 seconds

                        GPIO.output(TRIG, True)                  #Set TRIG as HIGH
                        time.sleep(0.00001)                      #Delay of 0.00001 seconds
                        GPIO.output(TRIG, False)                 #Set TRIG as LOW

                        while GPIO.input(ECHO)==0:               #Check whether the ECHO is LOW
                                pulse_start = time.time()              #Saves the last known time of LOW pulse

                        while GPIO.input(ECHO)==1:               #Check whether the ECHO is HIGH
                                pulse_end = time.time()                #Saves the last known time of HIGH pulse 

                        pulse_duration = pulse_end - pulse_start #Get pulse duration to a variable

                        distance = pulse_duration * 17150        #Multiply pulse duration by 17150 to get distance
                        distance = round(distance, 2)            #Round to two decimal points
                        print "Distance1:",distance - 0.5,"cm"

                        while distance < 10 :
                                if isRunning : 
                                        isObstacle = True
                                        pwm1.ChangeDutyCycle(VitesseRotationMax)
                                        pwm2.ChangeDutyCycle(VitesseRotationMin)
                                        pwm3.ChangeDutyCycle(VitesseRotationMax)
                                        pwm4.ChangeDutyCycle(VitesseRotationMin)

                                GPIO.output(TRIG, False)                 #Set TRIG as LOW
                                print "Waitng For Sensor To Settle"
                                time.sleep(2)                            #Delay of 2 seconds

                                GPIO.output(TRIG, True)                  #Set TRIG as HIGH
                                time.sleep(0.00001)                      #Delay of 0.00001 seconds
                                GPIO.output(TRIG, False)                 #Set TRIG as LOW
                                while GPIO.input(ECHO)==0:               #Check whether the ECHO is LOW
                                        pulse_start = time.time()              #Saves the last known time of LOW pulse

                                while GPIO.input(ECHO)==1:               #Check whether the ECHO is HIGH
                                        pulse_end = time.time()                #Saves the last known time of HIGH pulse 

                                pulse_duration = pulse_end - pulse_start #Get pulse duration to a variable

                                distance = pulse_duration * 17150        #Multiply pulse duration by 17150 to get distance
                                distance = round(distance, 2)            #Round to two decimal points
                                print "Distance:",distance - 0.5,"cm"
                        
                                
                                
                        
                        if isObstacle : 
                                isRunning = True
                                pwm1.ChangeDutyCycle(VitesseMotor1)
                                pwm2.ChangeDutyCycle(VitesseMotor2)
                                pwm3.ChangeDutyCycle(VitesseMotor3)
                                pwm4.ChangeDutyCycle(VitesseMotor4)
                                
                                GPIO.output(Moteur1A , False)
                                GPIO.output(Moteur1B , True)
                                
                                GPIO.output(Moteur2A , False)
                                GPIO.output(Moteur2B , True)

                                GPIO.output(Moteur3A , False)
                                GPIO.output(Moteur3B , True)
                                
                                GPIO.output(Moteur4A , False)
                                GPIO.output(Moteur4B , True)
                                isObstacle = False



isRunning = False
threadMesure = MesureDistance()

threadMesure.start()

           
HOST = "192.168.43.44"  # local host
PORT = 9000  # open port 7000 for connection
s = socket(AF_INET, SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(1)  # how many connections can it receive at one time
print "test"
conn, addr = s.accept()  # accept the connection
print "Connected by: ", addr
signal.signal(signal.SIGINT, handler)


while True:
        data = conn.recv(1024)  # how many bytes of data will the server receive
        print "data" ,data,"hh"
        data.rstrip()

        
        if data == "up":
                isRunning = True
                pwm1.ChangeDutyCycle(VitesseMotor1)
                pwm2.ChangeDutyCycle(VitesseMotor2)
                pwm3.ChangeDutyCycle(VitesseMotor3)
                pwm4.ChangeDutyCycle(VitesseMotor4)
                
                GPIO.output(Moteur1A , False)
                GPIO.output(Moteur1B , True)
                
                GPIO.output(Moteur2A , False)
                GPIO.output(Moteur2B , True)

                GPIO.output(Moteur3A , False)
                GPIO.output(Moteur3B , True)
                
                GPIO.output(Moteur4A , False)
                GPIO.output(Moteur4B , True)
                
                
        elif data == "down":
                pwm1.ChangeDutyCycle(VitesseMotor1)
                pwm2.ChangeDutyCycle(VitesseMotor2)
                pwm3.ChangeDutyCycle(VitesseMotor3)
                pwm4.ChangeDutyCycle(VitesseMotor4)
                GPIO.output(Moteur1A , True)
                GPIO.output(Moteur1B , False)
                   
                GPIO.output(Moteur2A , True)
                GPIO.output(Moteur2B , False)

                GPIO.output(Moteur3A , True)
                GPIO.output(Moteur3B , False)

                GPIO.output(Moteur4A , True)
                GPIO.output(Moteur4B , False)

        elif data == "left":
                pwm1.ChangeDutyCycle(VitesseRotationMin)
                pwm2.ChangeDutyCycle(VitesseRotationMax)
                pwm3.ChangeDutyCycle(VitesseRotationMin)
                pwm4.ChangeDutyCycle(VitesseRotationMax)
                
                
        elif data == "right":
                pwm1.ChangeDutyCycle(VitesseRotationMax)
                pwm2.ChangeDutyCycle(VitesseRotationMin)
                pwm3.ChangeDutyCycle(VitesseRotationMax)
                pwm4.ChangeDutyCycle(VitesseRotationMin)
                
                
        elif data == "stop":
                isRunning = False
                GPIO.output(Moteur1A , False)
                GPIO.output(Moteur1B , False)                   
                GPIO.output(Moteur1E , False)
                GPIO.output(Moteur2A , False)
                GPIO.output(Moteur2B , False)                   
                GPIO.output(Moteur2E , False)
                GPIO.output(Moteur3A , False)
                GPIO.output(Moteur3B , False)                   
                GPIO.output(Moteur3E , False)
                GPIO.output(Moteur4A , False)
                GPIO.output(Moteur4B , False)                   
                GPIO.output(Moteur4E , False)
        elif data == "close":
                conn.close()
                conn, addr = s.accept()
        elif data == "settings":
                sep = ' '
                buf = ''
                while sep not in buf:
                        buf += conn.recv(1);
                VitesseMotor1 = int(buf)
                #VitesseMotor1 = conn.recv(4);
                print "m1 : ",VitesseMotor1
                buf = ''
                while sep not in buf:
                        buf += conn.recv(1);
                VitesseMotor2 = int(buf)
                print "m2 : ",VitesseMotor2
                buf = ''
                while sep not in buf:
                        buf += conn.recv(1);
                VitesseMotor3 = int(buf)
                print "m3 : ",VitesseMotor3
                buf = ''
                while sep not in buf:
                        buf += conn.recv(1);
                VitesseMotor4 = int(buf)
                print "m4 : ",VitesseMotor4
                buf = ''
                while sep not in buf:
                        buf += conn.recv(1);
                VitesseRotationMax = int(buf)
                print "max : ",VitesseRotationMax
                buf = ''
                while sep not in buf:
                        buf += conn.recv(1);
                VitesseRotationMin = int(buf)
                print "min : ",VitesseRotationMin

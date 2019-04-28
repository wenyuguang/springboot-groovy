//#pragma execution_character_set("gbk")
#include <QCoreApplication>
#include <client.h>
#include <stdio.h>
#include <iostream>
#include<qhostinfo.h>
#include<QNetworkInterface>
#include<QSettings>
#include<Windows.h>
#include<isyswin.h>
#include<isysinfo.h>

#ifdef MB
    #define MB(1024 * 1024)
#endif
using namespace std;


int main(int argc, char *argv[])
{
//    int a = 0xbabe;
//    cout << a << endl;
//    QByteArray ba;
//    ba.resize(6);
//    ba[0] = 0x3c;
//    ba[1] = 0xb8;
//    ba[2] = 0x64;
//    ba[3] = 0x18;
//    ba[4] = 0xca;
//    qDebug() <<ba.size();
//    ba.data()[5] = 0x31;
//    qDebug()<<"[]"<<ba[2]; //[] d
//    qDebug()<<"at()"<<ba.at(2); //at() d
//    qDebug()<<"data()"<<ba.data()[2]; //data() d
//    qDebug()<<"constData()"<<ba.constData()[2]; //constData() d
//    qDebug()<<"constData()"<<ba.constData()[5]; //constData() 1

//    int n = 63;
//    qDebug()<<QByteArray::number(n);              // returns "63"
//    qDebug()<<QByteArray::number(a, 16);          // returns "3f"
//    qDebug()<<QByteArray::number(n, 16).toUpper();  // returns "3F"
//    qDebug()<<QByteArray::number(n, 2);          // returns "111111"
//    qDebug()<<QByteArray::number(n, 8);          // returns "77"

//    testSimpleTcpSocketClientDemo();

    QString machineName = QHostInfo::localHostName();
    qDebug() << "Local Host Name" << machineName;
//    QString ip = "";

//    QList<QNetworkInterface> interFaceList = QNetworkInterface::allInterfaces();
//    for(int i=0; i< interFaceList.size(); i++){
//        QNetworkInterface interface = interFaceList.at(i);
//        if(interface.flags().testFlag(QNetworkInterface::IsRunning)){
//            QList<QNetworkAddressEntry> entryList = interface.addressEntries();
//            foreach(QNetworkAddressEntry entry, entryList){
//                if(QAbstractSocket::IPv4Protocol == entry.ip().protocol() &&
//                        entry.ip() != QHostAddress::LocalHost){
//                    ip = entry.ip().toString();
//                    break;
//                }
//            }
//        }
//    }
//    qDebug() << "ip is :" << ip;

    QString strMac;

    QList<QNetworkInterface> netList = QNetworkInterface::allInterfaces();
    foreach(QNetworkInterface item, netList)
    {
        if((QNetworkInterface::IsUp & item.flags()) && (QNetworkInterface::IsRunning & item.flags()))
        {
            if(strMac.isEmpty() || strMac < item.hardwareAddress())
            {
                strMac = item.hardwareAddress();
            }
        }
    }
    qDebug() << "mac is : " << strMac;

    QSettings *CPU = new QSettings("HKEY_LOCAL_MACHINE\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0", QSettings::NativeFormat);
    QString m_cpuDescribe = CPU->value("ProcessorNameString").toString();
    delete CPU;
    qDebug() << "cpu model : " << m_cpuDescribe;

    ISysInfo* pISys = new ISysWin();//调用WINDOWS实现的子类
    int counter = 0;
    while (counter ++ < 5)
    {
        int nCpuRate = -1;
        int nMemTotal = -1;
        int nMemUsed = -1;
        int nDiskTotal = -1;
        int nDiskUsed = -1;
        int nProcessMemRate = -1;
        QMap<int,QString> pidMap;

        pISys->GetSysCpu(nCpuRate);
        qDebug()<<"CPU Rate:"<<nCpuRate<<"%";

        pISys->GetSysMemory(nMemTotal,nMemUsed);
        qDebug()<<"Mem Total:"<<nMemTotal<<"\t Mem Used:"<<nMemUsed;

        pISys->GetSysDisk(nDiskTotal,nDiskUsed);
        qDebug()<<"Disk Total:"<<(nDiskTotal/1024.0)<<"GB \t Disk Used:"<<(nDiskUsed/1024.0)<<"GB";

        pISys->GetProcessMemory(6472,nProcessMemRate);
        qDebug()<<"PID:6472;\t Mem Rate:"<<nProcessMemRate<<"%";

        pidMap = pISys->GetAllProcess();
        qDebug()<<"Process Number:"<<pidMap.size();

        qDebug()<<"-------------------------------------------------------------------";
        Sleep(1000);
    }
    delete pISys;

    SYSTEM_INFO systemInfo;
    GetSystemInfo(&systemInfo);
    qDebug() << "cpu numbers : " << systemInfo.dwNumberOfProcessors;

    MEMORYSTATUSEX statusex;
//    statusex.dwLength = sizeof (statusex);
    GlobalMemoryStatusEx(&statusex);
    qDebug() << "屋里内存使用率" << statusex.dwMemoryLoad;
//    qDebug() << "" << statusex.dwMemoryLoad;
//    qDebug() << "" << statusex.dwMemoryLoad;
//    qDebug() << "" << statusex.dwMemoryLoad;
//    qDebug() << "" << statusex.dwMemoryLoad;
//    qDebug() << "" << statusex.dwMemoryLoad;
//    qDebug() << "" << statusex.dwMemoryLoad;

    return 0;
}

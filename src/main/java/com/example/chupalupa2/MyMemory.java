package com.example.chupalupa2;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
interface CLibrary extends Library {
    int PTRACE_ATTACH = 16;
    //команда подключения
    int PTRACE_DETACH = 17;
    //команда отключения
    int PTRACE_PEEKDATA = 2;
    //команда получения данных
    int PTRACE_POKEDATA = 5;

    //команда записи данных
    int ptrace(int request, int pid, Pointer addr, int data);
}

public class MyMemory {
    Pointer address;
    int pid;
    //pid потока,address-адрес в памяти
    CLibrary libc = (CLibrary) Native.loadLibrary("c",
            CLibrary.class);
    //загрузк библиотеки libc
    public MyMemory(int pid, String adrStr){
        this.pid = pid;
        address = new Pointer(Long.parseLong(adrStr, 16));
//преобразование адреса в лонг X 16
    }
    //используется фунция ptrace которая позволяет подключится к потоку
    public int read(){
        libc.ptrace(CLibrary.PTRACE_ATTACH, pid, null,0);
        int addressValue =
                libc.ptrace(CLibrary.PTRACE_PEEKDATA, pid, address, 0);
        libc.ptrace(CLibrary.PTRACE_DETACH, pid, null,0);
        return addressValue;
    }
    //считывание значение из потока
    public void write(int value){
        libc.ptrace(CLibrary.PTRACE_ATTACH, pid, null,0);
        libc.ptrace(CLibrary.PTRACE_POKEDATA, pid, address,
                value);
        libc.ptrace(CLibrary.PTRACE_DETACH, pid, null,0);
    }
//запись в поток
}


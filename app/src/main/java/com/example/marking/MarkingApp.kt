package com.example.marking

import android.app.Application

/**
 * Created by: androdev
 * Date: 27-03-2023
 * Time: 3:47 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

class MarkingApp : Application() {

    override fun onCreate() {
        super.onCreate()

        System.setProperty(
            "org.apache.poi.javax.xml.stream.XMLInputFactory",
            "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
            "org.apache.poi.javax.xml.stream.XMLOutputFactory",
            "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
            "org.apache.poi.javax.xml.stream.XMLEventFactory",
            "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }
}
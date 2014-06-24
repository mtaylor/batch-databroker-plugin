/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.jboss.databroker.plugins.batch.dataflownodes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataSink;
import com.jboss.databroker.plugins.batch.connectors.SimpleDataConsumer;

public class SimpleDataSink implements DataSink
{
    private static final Logger logger = Logger.getLogger(SimpleDataSink.class.getName());

    public SimpleDataSink(String name, Map<String, String> properties)
    {
        logger.log(Level.INFO, "SimpleDataSink: " + name + ", " + properties);

        _name       = name;
        _properties = properties;

        _sentHistory = new LinkedList<String>();

        _dataConsumer = new SimpleDataConsumer<String>(this, MethodUtil.getMethod(SimpleDataSink.class, "send", String.class));
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public Map<String, String> getProperties()
    {
        return Collections.unmodifiableMap(_properties);
    }

    public void send(String data)
    {
        logger.log(Level.INFO, "SimpleDataSink.send: data = " + data);
        _sentHistory.add(data);
    }

    public List<String> getSentHistory()
    {
        return _sentHistory;
    }

    @Override
    public Collection<Class<?>> getDataConsumerDataClasses()
    {
        Set<Class<?>> dataConsumerDataClasses = new HashSet<Class<?>>();

        dataConsumerDataClasses.add(String.class);
        
        return dataConsumerDataClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataConsumer<T> getDataConsumer(Class<T> dataClass)
    {
        if (dataClass == String.class)
            return (DataConsumer<T>) _dataConsumer;
        else
            return null;
    }

    private List<String> _sentHistory;
    
    private String               _name;
    private Map<String, String>  _properties;
    private DataConsumer<String> _dataConsumer;
}

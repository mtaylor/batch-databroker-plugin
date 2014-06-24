/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.jboss.databroker.plugins.batch.dataflownodes;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataService;
import com.jboss.databroker.plugins.batch.connectors.SimpleDataConsumer;
import com.jboss.databroker.plugins.batch.connectors.SimpleDataProvider;

public class SimpleDataService implements DataService
{
    private static final Logger logger = Logger.getLogger(SimpleDataService.class.getName());

    public SimpleDataService(String name, Map<String, String> properties)
    {
        logger.log(Level.INFO, "StringDataService: " + name + ", " + properties);

        _name       = name;
        _properties = properties;

        _dataConsumer = new SimpleDataConsumer<String>(this, MethodUtil.getMethod(SimpleDataService.class, "export", String.class));
        _dataProvider = new SimpleDataProvider<String>(this);
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

    public void export(String data)
    {
        logger.log(Level.INFO, "SimpleDataService.export: " + data);
    }

    public void dummyImport(String data)
    {
        logger.log(Level.INFO, "SimpleDataSource.dummyImport: " + data);

        _dataProvider.produce(data);
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
    @Override
    public Collection<Class<?>> getDataProviderDataClasses()
    {
        Set<Class<?>> dataProviderDataClasses = new HashSet<Class<?>>();

        dataProviderDataClasses.add(String.class);
        
        return dataProviderDataClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataProvider<T> getDataProvider(Class<T> dataClass)
    {
        if (dataClass == String.class)
            return (DataProvider<T>) _dataProvider;
        else
            return null;
    }

    private String               _name;
    private Map<String, String>  _properties;
    private DataConsumer<String> _dataConsumer;
    private DataProvider<String> _dataProvider;
}

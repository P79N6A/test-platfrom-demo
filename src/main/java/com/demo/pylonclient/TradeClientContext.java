package com.demo.pylonclient;

import me.ele.contract.client.ClientAttrs;
import me.ele.contract.client.IClientContext;
import me.ele.contract.client.ITemplateClient;
import me.ele.core.CoreConsts;
import me.ele.core.CoreException;
import me.ele.core.CoreInitializer;
import me.ele.pylon.core.PylonCore;
import me.ele.pylon.core.config.ClientAlias;
import me.ele.rpc.client.Client;

import java.util.List;
import java.util.Map;

public class TradeClientContext implements IClientContext {

    @Override
    public void initClients() {
        CoreInitializer.init(CoreConsts.DEFAULT_CONFIG_PATH);
    }

    @Override
    public void initClients(String path) {
        CoreInitializer.init(path);
    }

    @Override
    public <T> T getClient(Class<T> clazz) {
        return PylonCore.get().getClient(clazz).build(clazz);
    }

    public <T> T getClient(Class<T> clazz, Map<String, Object> metas) {
        return PylonCore.get().getClient(clazz).build(clazz, metas);
    }

    public <T> T getClient(Class<T> clazz, ClientAttrs clientAttrs) {
        return PylonCore.get().getClient(clazz).build(clazz, clientAttrs);
    }

    @Override
    public <T> T getClient(String name, Class<T> clazz) {
        return PylonCore.get().getClient(name, clazz).build(clazz);
    }

    public <T> T getClient(String name, Class<T> clazz, Map<String, Object> metas) {
        return PylonCore.get().getClient(name, clazz).build(clazz, metas);
    }

    public <T> T getClient(String name, Class<T> clazz, ClientAttrs clientAttrs) {
        return PylonCore.get().getClient(name, clazz).build(clazz, clientAttrs);
    }

    public <T> T getClient(String name, String group, Class<T> clazz) {
        return PylonCore.get().getClient(name, group, clazz).build(clazz);
    }

    public <T> T getClient(String name, String group, Class<T> clazz, Map<String, Object> metas) {
        return PylonCore.get().getClient(name, group, clazz).build(clazz, metas);
    }

    public <T> T getClient(String name, String group, Class<T> clazz, ClientAttrs clientAttrs) {
        return PylonCore.get().getClient(name, group, clazz).build(clazz, clientAttrs);
    }

    public <T> T getClientByAlias(String alias, Class<T> clazz) {
        ClientAlias config = PylonCore.get().getClientByAlias(alias);
        checkAliasIsRight(config, clazz);
        Client<?> client = PylonCore.get().getClient(config.getConf().getName(), config.getConf().getGroup(), clazz);
        if (config.getAttrs() == null)
            return client.build(clazz);
        else
            return client.build(clazz, config.getAttrs());
    }

    public <T> T getClientByAlias(String alias, Class<T> clazz, ClientAttrs clientAttrs) {
        ClientAlias config = PylonCore.get().getClientByAlias(alias);
        checkAliasIsRight(config, clazz);
        Client<?> client = PylonCore.get().getClient(config.getConf().getName(), config.getConf().getGroup(), clazz);
        return client.build(clazz, clientAttrs);
    }

    public <T> T getClientByAlias(String alias, Class<T> clazz, Map<String, Object> metas) {
        ClientAlias config = PylonCore.get().getClientByAlias(alias);
        checkAliasIsRight(config, clazz);
        Client<?> client = PylonCore.get().getClient(config.getConf().getName(), config.getConf().getGroup(), clazz);
        return client.build(clazz, metas);
    }

    private void checkAliasIsRight(ClientAlias config, Class<?> clazz) {
        if (config.getIface() != null && config.getIface() != clazz)
            throw new CoreException(String.format("Undeclared alias client(%s) group(%s) interface(%s) found!", config.getConf().getName(),
                    config.getConf().getGroup(), clazz.getName()));
    }

    public <T> List<T> getClients(Class<T> clazz) {
        return PylonCore.get().getClient(clazz).buildAll(clazz, null);
    }

    public <T> List<T> getClients(Class<T> clazz, ClientAttrs clientAttrs) {
        return PylonCore.get().getClient(clazz).buildAll(clazz, clientAttrs);
    }

    public <T> List<T> getClients(String name, Class<T> clazz) {
        return PylonCore.get().getClient(name, clazz).buildAll(clazz, null);
    }

    public <T> List<T> getClients(String name, Class<T> clazz, ClientAttrs clientAttrs) {
        return PylonCore.get().getClient(name, clazz).buildAll(clazz, clientAttrs);
    }

    public <T> List<T> getClients(String name, String group, Class<T> clazz) {
        return PylonCore.get().getClient(name, group, clazz).buildAll(clazz, null);
    }

    public <T> List<T> getClients(String name, String group, Class<T> clazz, ClientAttrs clientAttrs) {
        return PylonCore.get().getClient(name, group, clazz).buildAll(clazz, clientAttrs);
    }

    public <T> List<T> getClientsByAlias(String alias, Class<T> clazz) {
        ClientAlias config = PylonCore.get().getClientByAlias(alias);
        return getClientsByAlias(alias, clazz, config.getAttrs());
    }

    public <T> List<T> getClientsByAlias(String alias, Class<T> clazz, ClientAttrs clientAttrs) {
        ClientAlias config = PylonCore.get().getClientByAlias(alias);
        checkAliasIsRight(config, clazz);
        Client<?> client = PylonCore.get().getClient(config.getConf().getName(), config.getConf().getGroup(), clazz);
        return client.buildAll(clazz, clientAttrs);
    }

    @Override
    public ITemplateClient build() {
        return null;
    }

    @Override
    public ITemplateClient build(String name) {
        return null;
    }
}

# Island-Linker

## 目录说明

Horizontal：面向横向数据联邦的分组聚合查询算法

Vertical：面向纵向数据联邦的分组聚合查询算法

## 环境需求

- Ubuntu 18.04
- Docker

- Java 11
- Apache Maven 3.6.0+

**注意：**以下测试为在 Ubuntu 系统上进行，但理论上其他操作系统也完全能够正常运行，如：macOS、Windows

## 快速开始

- 首先，确保相关环境齐全。
- 【可选】向当前帐户授予 Docker 权限，可以尝试运行 “docker run hello-world”。如果没有看到包含 “Permission Denied” 字样的消息，则无需继续执行此步骤。

```shell
# 检查 Docker 组是否存在
cat /etc/group | grep docker

# 如果缺少 Docker 组，请创建.
sudo groupadd docker

# 将当前用户添加到 Docker 组.
sudo usermod -aG docker $USER
```

- 克隆 Git 仓库

```shell
git@github.com:ZichengCao/Island-Linker.git
```

- 选择指定项目

```shell
# 横向数据联邦的分组聚合查询算法
cd Horizontal

# 纵向数据联邦的分组聚合查询算法
cd Vertical
```

- 使用 Docker 部署数据联邦环境，并初始化数据方

```shell
docker pull postgres:13

chmod +x ./package.sh && ./package.sh
chmod +x ./deploy.sh && ./deploy.sh
```

- 您可以通过执行以下命令来验证数据容器是否已成功初始化

```shell
# 如果正确显示数据表的 Java 版本和行数，则表示初始化成功。

docker exec -u postgres container1 bash -c 'java --version && psql postgres -c "select count(*) from lineitem"'
docker exec -u postgres container2 bash -c 'java --version && psql postgres -c "select count(*) from lineitem"'
docker exec -u postgres container3 bash -c 'java --version && psql postgres -c "select count(*) from lineitem"'
```

- 启动所有数据方服务

```shell
cd release
./start-server.sh
```

- 执行查询

```shell
# 可以通过 java -jar core.jar -h 查看参数说明
java -jar core.jar -i test_sum.sql -p private
```

## 运行自定义查询

如果希望运行自定义查询，则应执行以下步骤，以及对某些配置文件的修改：

1. 构建多个 Docker 容器，配置包括 Postgres 13+ 和 Java 11+，并将容器的指定端口（可以通过修改 `container/config.json` 中的 `serverPort` 项来指定）映射到主机的自定义端口上（可通过 `deploy.sh` 中的创建docker 容器命令修改）
2. 修改 `release/config.json` 文件，使用上一步中配置的 "主机机器的本地网络 IP + 映射到主机机器的端口号" 表示各个容器。
3. 修改 `release/container/config.json` 文件，配置相应容器的 Postgresql 数据库的访问链接。

对于使用真实的多台机器环境而不是 Docker 容器的情况，整体步骤类似，但需要针对实际情况进行配置和操作。


# 0623Start

## :clipboard: 목차

- :books: <a href="#outline">개요</a>
- :wrench: <a href="#tech">기술 스택</a>
- :scroll: <a href="#erd">ERD(Entity-Relation Diagram)</a>
- :family: <a href="#team">팀원 역할 소개</a>
- :bookmark_tabs: <a href="#function">기능</a>
- :mag_right: <a href="#fullfill">보완할점</a>

# :books: <a name="outline">개요</a>
<br>

> **프로젝트** : DB 활용 자유 주제 - 모의 경매 프로그램
>
> **프로젝트 이름** : Now Auction
>
> **분류** : 팀 프로젝트
>
> **제작 기간** : 2023.06.23 ~ 2023.07.05
>
> **주제 선정 이유** : 경매와 같이 실시간으로 시간과 가격이 변동되는 프로그램을 구현하고 싶어서
> 
> **주 사용자층** : 경매를 하고 싶지만 경매에 대해 익숙하지 않은 사람
>
> **프로젝트 목표** :
> 1. 사용자에게 경매에 대한 경험 제공
> 2. 실시간 DB 상호작용
> 3. 실제 경매사이트랑 유사하게 만들어보기

<br>

# :scroll: <a name="function">기능</a>

- <a href="#fun0">0.&nbsp;홈화면</a>
  - 0-1. 튜토리얼
  - 0-2. 회원가입
- <a href="#fun1">1.&nbsp;메인화면</a>
 	- 1-1. 경매참여화면
- <a href="#fun2">2.&nbsp;마이페이지</a>
   	- 2-1. 물품등록화면
   	- 2-2. 개인정보변경

### <a name="fun0">홈화면</a>
<img src="https://github.com/Psh230412/0623Start/assets/134483516/72831eb8-3fa9-462c-980c-2dc6820a6861"/>

### 튜토리얼
<img src="https://github.com/Psh230412/0623Start/assets/134483516/e84a5dae-60db-4175-96b8-0b89c19d8b5e"/>

##### 프로그램을 사용하는 유저에 연령대가 다양할 것이라 판단되어 튜토리얼을 만들어 사용법을 제공

### 회원가입
<img src="https://github.com/Psh230412/0623Start/assets/134483516/aaa8f3a9-3e21-4f5d-852c-4c85ba2bdc1b"/>

### <a name="fun1">메인화면</a>
<img src="https://github.com/Psh230412/0623Start/assets/134483516/6022add2-433e-40e2-b11b-7e73c59c566e"/>

##### 인터넷 쇼핑몰 사이트를 참고하여 물품을 정렬하는 여러가지 소스를 제공
##### 인기 순 정렬은 단순히 입찰 수로 결정할 경우 소수의 사람이 물품의 인기를 조절할 수 있을 수 있다고 생각
##### 서로 다른 유저가 입찰할 때 마다 카운트를 세어 중복입찰을 할 경우 인기가 오르는 걸 방지

###  경매참여화면
<img src="https://github.com/Psh230412/0623Start/assets/134483516/ceb285f9-5cd3-4624-8211-8f80cf9afe02"/>

##### 남은 시간이 1분 미만일 때 입찰을 하면 입찰 시간을 1분으로 늘려주는 기능 구현
##### 이 때 과도하게 입찰이 늘어지는 것을 방지하기 위해 최소입찰가는 현재입찰가의 +5%로 설정

### <a name="fun2">마이페이지</a>
<img src="https://github.com/Psh230412/0623Start/assets/134483516/9bf4c2eb-20a4-491d-9522-5257cd41ee1d"/>

##### 등록 물품/입찰 물품을 최근 시간순으로 정렬시키고 현재시간으로 부터 7일이 지나면 기록이 지워지게 설정

### 물품등록화면
<img src="https://github.com/Psh230412/0623Start/assets/134483516/928c8139-008e-4c2b-847a-8d90ac6f8786"/>

##### 최대한 등록한 물품에 대한 많은 정보를 제공하고자 사진을 최대 4개까지 등록할 수 있도록 설정

### 개인정보변경
<img src="https://github.com/Psh230412/0623Start/assets/134483516/40f49179-5410-4aa6-89f8-0c2f586c672f"/>

# :wrench: <a name="tech">기술 스택</a>

<h4>데이터베이스</h4>
<div align="left">
   <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
</div> 
<h4>언어</h4>
<div align="left">
    <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=Java&logoColor=white"/>
</div>

<h4>API</h4>

[![MyGet pre-release](https://img.shields.io/myget/quartznet/vpre/Quartz)](#)

<h4>협업도구</h4>
<div align="left">
   <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white" />
   <img src="https://img.shields.io/badge/FIGMA-F24E1E?style=for-the-badge&logo=figma&logoColor=white" />
</div>

# :scroll: <a name="erd">ERD</a>

## 초기 ERD
<img src="https://github.com/Psh230412/0623Start/assets/110301333/18db84d4-60bd-4e4a-bef4-4cfb32e1143a" width="100%"/>

## 현재 ERD
<img src="https://github.com/Psh230412/0623Start/assets/110301333/3490b2b3-f836-43f1-a86f-0598953c6e90" width="100%"/>

### 변경이유
1. 낙찰/유찰된 물품을 포함한 전체 물품을 관리하는 copy_auction과 </br>
   경매 진행 중인 물품을 관리하는 auction으로 구분 하여 목적에 맞게 테이블 활용이 용이하도록 함
2. 낙찰/유찰 여부를 한 테이블로 관리함


# :family: <a name="team">팀원 역할 소개</a>
<br>

| 이름 | 박상현 - 팀장 | 김명완 | 김민아 | 성지수 | 이정빈
| :---: | :----------: | :----------: | :----------: | :----------: | :----------: |
| 역할 | 기획 </br> 업무 현황 파악 </br> 상품등록 </br> 회원정보변경 | DB관리 </br> 물품검색 </br> 캐시구현 | 디자인 총괄 </br> 사용설명서 | 회원가입 </br> 로그인 </br> 디버깅 </br> 메인 분류 | 스케줄러 </br> 마이페이지 </br> 메인 정렬 </br> 입찰화면 |



# :mag_right: <a name="#fullfill">보완할점</a>
### 1. 금액 충전, 결제시스템 추가
### 2. 입찰한 물품에 상위입찰이 들어오면 기존 입찰자에게 정보제공
### 3. 낙찰/유찰된 내역 삭제, 유찰물품 재등록
### 4. 이미 등록한 물품에 대한 정보 수정 기능

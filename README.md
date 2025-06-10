
# HOMS(Hanhwa Order Management System)
<p align="middle" style="margin: 0; padding: 0;">
  <img width="250px" src="https://github.com/user-attachments/assets/f3a0f96a-a148-4b7e-ac0f-f98d6ab7b30b">
</p>

## 팀원 소개
  |<img src="https://github.com/user-attachments/assets/973ece43-16c1-4b23-95fd-0bb34edcdbc8" width="150" height="150"/>|<img src="https://github.com/user-attachments/assets/973ece43-16c1-4b23-95fd-0bb34edcdbc8" width="150" height="150"/>|<img src="https://github.com/user-attachments/assets/973ece43-16c1-4b23-95fd-0bb34edcdbc8" width="150" height="150"/>|<img src="https://github.com/user-attachments/assets/973ece43-16c1-4b23-95fd-0bb34edcdbc8" width="150" height="150"/>|
|:-:|:-:|:-:|:-:|
|오영광<br/>[@OhGlory](https://github.com/OhGlory)|김범석<br/>[@g00dbyul](https://github.com/g00dbyul)|박서준<br/>[@pppseojun](https://github.com/pppseojun)|홍재민<br/>[@MSP-31](https://github.com/MSP-31)|
|팀장, 백엔드 개발 리드|인프라 설계 및 구축|프론트엔드 개발 리드|풀스택 개발|


## 프로젝트 소개
한화솔루션 케미칼 부문의 B2B OMS(Order Management System)을 고도화 하기 위한 프로젝트 입니다. 

## 배경
기존의 주문 시스템은 약 49% 정도 이메일, FAX, 전화, 문서 형태의 발주서를 제출하고 영업팀이 수작업으로 처리 됩니다. 이 과정에서 4% 정도의 오타 및 누락 등으로 인해 처리 지연의 문제가 발생합니다. 또한, 소통 단절의 문제로 인해 주문 및 배송 조회 시 별도 문의가 필요하며 이력 관리의 어려움도 있습니다. 이런 문제를 해결하기 위해 주문 통합 관리 플랫폼 HOMS(Hanhwa Order Management System)을 개발하게 되었습니다.

## 제안
수작업으로 처리 되는 주문 프로세스를 엑셀 템플릿 기반의 원클릭 주문과 플랫폼 통합으로 주문 -> 출고 -> 배송 -> 정산 -> 클레임 관리를 일원화 합니다. 실시간 채팅 기능을 지원해 기업 고객의 민원에 효율적으로 대응할 수 있습니다. 또한, 데이터 시각화 및 통계 자료를 제공해 주문과 관련된 지표를 점검하고 관리할 수 있습니다.

## 주요 기능
### 엑셀 템플릿 주문
![2025-06-08__16 48 14](https://github.com/user-attachments/assets/a63ebbd9-28a2-4b67-a91e-96246ba9cf18)
엑셀 템플릿에 주문 제품과 수량을 입력하고 업로드 하면 엑셀 파일을 파싱해 주문 내역을 생성합니다.

### 실시간 채팅
![2025-06-08__16 41 45](https://github.com/user-attachments/assets/24cf808f-6ace-4874-a1e3-940e23198cfb)
실시간 채팅을 통해 기업 고객과 주문 담당자 간에 주문에 대한 문의, 주문에 대한 안내를 효율적으로 처리할 수 있습니다.

### 주문 상태 추적
<img width="1231" alt="스크린샷 2025-06-10 오전 10 23 16" src="https://github.com/user-attachments/assets/3302aedb-05dc-4ffd-9a9a-7c7f05c27cc8" />
주문 진행 상태부터 배송 상태, 그리고 클레임까지 관리할 수 있습니다.

### 대시보드
![2025-06-09__17 35 16](https://github.com/user-attachments/assets/f79fae58-10d9-48f3-aa59-28adc2cab8eb)
주문, 배송, 매출과 관련된 정보를 시각화 할 수 있습니다.

## 기대 효과
### 업무 효율화
수작업으로 처리 되던 작업을 자동화해 반복 작업을 최소화 하고, 주문 상태에 대한 빠르게 파악할 수 있습니다.

### 운영 비용 절감
오타, 누락으로 인한 처리 지연, 커뮤니케이션으로 인한 지연을 감소 시킬 수 있습니다.

### 데이터 기반 운영
주문 데이터를 기반으로 분석 및 수요 예측이 가능합니다.

### 한화 그룹 내 확장
유사 프로세스를 범용적으로 적용할 수 있습니다.

## 기술 스택

### &nbsp;　[ Backend ]
&nbsp;&nbsp;&nbsp;&nbsp; ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/Springdata jpa-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" style="border-radius: 5px;">

### &nbsp;　[ Frontend ]
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white" style="border-radius: 5px;"/>
<img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white" style="border-radius: 5px;"/>
<img src="https://img.shields.io/badge/TypeScript-1572B6?style=for-the-badge&logo=TypeScript&logoColor=white" style="border-radius: 5px;"/>
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
<img src="https://img.shields.io/badge/pinia-gold?style=for-the-badge&logo=Pinia&logoColor=white" style="border-radius: 5px;"/>

### &nbsp;　[ DB ]
&nbsp;&nbsp;&nbsp;</a>
<img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/Redis-E34F26?style=for-the-badge&logo=redis&logoColor=white" style="border-radius: 5px;">

### &nbsp;　[ CI/CD ]
&nbsp;&nbsp;&nbsp;&nbsp;</a>
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/githubactions-007396?style=for-the-badge&logo=githubactions&logoColor=white" style="border-radius: 5px;">


## 산출물

<details>
<summary><strong>📂 프로젝트 기획서</strong></summary>

[프로젝트 기획서 바로가기](https://docs.google.com/document/d/1XbMT1HGPlgpH06b82P607fU4Rn3Fom8In8m-o_wnFcM/edit?usp=sharing)

</details>

<details>
<summary><strong>📂 요구사항 정의서</strong></summary>

[요구사항 명세서 바로가기](https://docs.google.com/spreadsheets/d/1IPizdsH3qvIH6CO-poGY1lSNPvnwErczsBrQjVnF3JU/edit?usp=sharing)

</details>

<details>
<summary><strong>📂 시스템 아키텍쳐</strong></summary>

![300drawio](https://github.com/user-attachments/assets/561161fd-2170-436e-9b59-2cd200907a87)

</details>

<details>
<summary><strong>📂 WBS(Work Breakdown Structure)</strong></summary>

[WBS 바로가기](https://docs.google.com/spreadsheets/d/1IPizdsH3qvIH6CO-poGY1lSNPvnwErczsBrQjVnF3JU/edit?usp=sharing)

</details>


<details>
<summary><strong>📂 ERD(Entity-Relationship Diagram)</strong></summary>


![HOMS](https://github.com/user-attachments/assets/4fa1ee25-da66-4c62-b368-d034c1be44e3)



</details>

<details>
<summary><strong>📂 화면설계서</strong></summary>

[화면설계서 바로가기](https://www.figma.com/design/q944fA6GYd5sTxmnAI1m5D/화면-설계서?node-id=0-1&t=zvr2lsrwj7Su31aZ-1)

</details>

<details>
<summary><strong>📂 프로그램 사양서</strong></summary>

[프로그램 사양서 바로가기](https://playdatacademy.notion.site/API-1d6d943bcac2810a938bd9ac0cf706da?pvs=4)

</details>

<details>
<summary><strong>📂 단위 테스트 결과서</strong></summary>

[단위 테스트 결과서 바로가기](https://playdatacademy.notion.site/1d6d943bcac2818eb324eb893fbb1f77?pvs=4)

</details>

<details>
<summary><strong>📂 UI/UX 단위 테스트</strong></summary>

[UI/UX 단위 테스트 바로가기](https://playdatacademy.notion.site/UI-UX-203d943bcac280a8a3a7fe7263847027?pvs=4)

</details>


<details>
<summary><strong>📂 CI/CD 계획서</strong></summary>

[CI/CD 계획서 바로가기](https://playdatacademy.notion.site/CI-CD-1d6d943bcac281d5a58bf908521c9b6c?source=copy_link)

</details>


<details>
<summary><strong>📂 통합 테스트 결과서</strong></summary>

[통합 테스트 결과서 바로가기](https://playdatacademy.notion.site/1d6d943bcac28132b060cf911ec43ab6?source=copy_link)

</details>

## 회고
| 팀원 | 회고록 |
| ---- | ------ |
| 오영광 |  |
| 김범석 |  |
| 홍재민 |  |
| 박서준 |  |


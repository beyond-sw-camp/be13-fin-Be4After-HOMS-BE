
# HOMS(Hanhwa Order Management System)
<p align="middle" style="margin: 0; padding: 0;">
  <img width="250px" src="https://github.com/user-attachments/assets/f3a0f96a-a148-4b7e-ac0f-f98d6ab7b30b">
</p>

## 팀원 소개
  | <img src="https://github.com/user-attachments/assets/a0586f90-9c45-4d7a-894d-a297ad4ef9f1" width="150" height="150"> | <img src="https://github.com/user-attachments/assets/dae8b52f-052b-4fc1-a4c4-ddaa9e3aa6bd" width="150" height="150"> | <img src="https://github.com/user-attachments/assets/8a78e39e-37af-449b-8bfc-787e22b2eb22" width="150" height="150"> | <img src="https://github.com/user-attachments/assets/49d61e1d-ed1f-4e49-b492-0e577816c61a" width="150" height="150"> |
|:-:|:-:|:-:|:-:|
|오영광<br/>[@OhGlory](https://github.com/OhGlory)|김범석<br/>[@g00dbyul](https://github.com/g00dbyul)|박서준<br/>[@pppseojun](https://github.com/pppseojun)|홍재민<br/>[@MSP-31](https://github.com/MSP-31)|
|팀장, 백엔드 개발 리드|인프라 설계 및 구축|프론트엔드 개발 리드|풀스택 개발|


## 프로젝트 소개
HOMS(Hanwha Order Management System)은 기존 주문 프로세스를 개선해 주문 관련 업무의 효율성 향상을 목표로 하여 한화솔루션 케미칼 부문의 B2B OMS(Order Management System)을 고도화 하기 위한 프로젝트 입니다. 

## 배경
### 기존 주문 프로세스의 문제점
기존의 주문 시스템은 약 49% 정도 이메일, FAX, 전화, 문서 형태의 발주서를 제출하고 영업팀이 수작업으로 처리 됩니다. 이 과정에서 4% 정도의 오타 및 누락 등으로 인해 처리 지연의 문제가 발생합니다. 또한, 소통 단절의 문제로 인해 주문 및 배송 조회 시 별도 문의가 필요하며 이력 관리의 어려움도 있습니다. 

### 주문 통합 관리 플랫폼의 필요성
이런 문제를 해결하기 위해 주문 통합 관리 플랫폼 HOMS(Hanhwa Order Management System)을 개발하게 되었습니다.

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
| 김범석 | 다른 팀들 보다 인원은 적은데 개발 할 것은 많아서 걱정 되었습니다. 힘들었지만 팀원들과 함께한 노력, 강사님 매니저님들의 관심으로 프로젝트를 마무리 지을 수 있었습니다. <br/><br/>개발하면서 왜 이렇게 해야하지? 이론만 알고 머리로 이해하지 못했던 부분들을 깨달으면서 개발 역량을 성장시킬 수 있었습니다. |
| 홍재민 | 안녕하세요, 저는 홍재민입니다. 먼저 이 뜻깊은 프로젝트를  마치게 되어 큰 영광이며, 이 자리에 설 수 있도록 함께해 주신 모든 분께 감사의 인사를 전합니다.<br/><br/> 이번 HOMS 프로젝트는 단순한 주문관리시스템을 구축하는 것이 아니라, 보다 효율적인 프로세스를 설계하고, 사용자 중심의 편리한 시스템을 개발하는 데 초점을 맞춘 프로젝트였습니다.<br/><br/> 처음 시작할 때에는 많은 도전이 있었지만, 팀원들의 열정과 협력 덕분에 성공적으로 마무리할 수 있었습니다.<br/><br/> 이 자리를 빌려, 프로젝트를 함께 이끌어 준 Be4after팀원들에게 깊은 감사를 전합니다. 또한, 지속적으로 피드백을 주며 성장할 수 있도록 도와주신 문인수 강사님과 정민환 멘토님께도 감사드립니다.<br/><br/> 이 프로젝트는 팀원 모두가 함께 힘을 모은 노력의 결과라고 생각합니다. 앞으로도 최고의 프로젝트와 협업을 할 수 있도록 최선을 다하겠습니다. 다시 한번 감사드리며, 앞으로도 좋은 모습으로 찾아뵙겠습니다. <br/><br/>감사합니다! |
| 박서준 |  |



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
기존의 주문 시스템은 약 49% 정도 이메일, FAX, 전화, 문서 형태의 발주서를 제출하고 영업팀이 수작업으로 처리 됩니다. 이 과정에서 4% 정도의 __오타 및 누락__ 등으로 인해 처리 지연의 문제가 발생합니다. 또한, __소통 단절의 문제__ 로 인해 주문 및 배송 조회 시 별도 문의가 필요하며 이력 관리의 어려움도 있습니다. 

### 주문 통합 관리 플랫폼의 필요성
주문 프로세스의 개선 방향
- 수작업에서 자동화로 업무 시간 단축<br/>
- 오타 및 누락으로 인한 추가적인 업무 차단<br/>
- 실시간 소통으로 인한 민원 처리<br/>
- 실시간 상태 조회 및 주문 내역 관리 <br/>
<br/>
비효율적이고 오류에 취약한 기존 주문 방식을 개선하고, 실시간 소통과 정확한 주문 이력 관리가 가능한 시스템이 요구되었습니다. 이에 따라 문제를 해결하기 위해 주문 통합 관리 플랫폼 <em>HOMS(Hanhwa Order Management System)</em> 을 개발하게 되었습니다.

## 제안
주문 프로세스 간소화
- 엑셀 템플릿을 통한 일괄 주문과 E-Commerce와 비슷한 UI로 주문을 가능하게 해 주문 프로세스를 간소화 했습니다.

실시간 소통
- 실시간 채팅을 통해 기업 고객과 담당자 간 소통이 가능하며, 주문과 관련된 민원 사항을 즉각적으로 처리할 수 있습니다.

주문 상태 조회
- 주문의 진행 상태 및 배송, 클레임을 실시간으로 조회할 수 있습니다.

데이터 시각화
- 주문과 관련된 내역과 데이터들을 시각화하여 업무에 관련된 인사이트를 제공합니다.

위와 같은 개선 방향을 적용한 주문 통합 관리 플랫폼으로 __주문 -> 출고 -> 배송 -> 정산 -> 클레임__ 관리를 일원화 합니다.

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
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" />
<img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white" />


### &nbsp;　[ Frontend ]
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white" style="border-radius: 5px;"/>
<img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white" style="border-radius: 5px;"/>
<img src="https://img.shields.io/badge/TypeScript-1572B6?style=for-the-badge&logo=TypeScript&logoColor=white" style="border-radius: 5px;"/>
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
<img src="https://img.shields.io/badge/pinia-gold?style=for-the-badge&logo=Pinia&logoColor=white" style="border-radius: 5px;"/>
<img src="https://img.shields.io/badge/SockJS-4D4D4D?style=for-the-badge&logo=socketdotio&logoColor=white" />
<img src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white" />

<img src="https://img.shields.io/badge/Vue_Router-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white" />


### &nbsp;　[ DB ]
&nbsp;&nbsp;&nbsp;</a>
<img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/Redis-E34F26?style=for-the-badge&logo=redis&logoColor=white" style="border-radius: 5px;">

### &nbsp;　[ CI/CD ]
&nbsp;&nbsp;&nbsp;&nbsp;</a>
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/githubactions-007396?style=for-the-badge&logo=githubactions&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge" />

### &nbsp;　[ INFRA ]
&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://img.shields.io/badge/CloudFront-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white" />
<img src="https://img.shields.io/badge/AWS_ECR-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white" />
<img src="https://img.shields.io/badge/AWS_ECS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white" />


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

![300drawio(3)](https://github.com/user-attachments/assets/653e5844-09a8-4dbb-a596-dcdbd7a75f85)



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
| 팀&nbsp;&nbsp;&nbsp;&nbsp;원 | 회고록 |
| ---- | ------ |
| 오영광 |  |
| 김범석 | 다른 팀들 보다 인원은 적은데 개발 할 것은 많아서 걱정 되었습니다. 힘들었지만 팀원들과 함께한 노력, 강사님 매니저님들의 관심으로 프로젝트를 마무리 지을 수 있었습니다. <br/>개발하면서 왜 이렇게 해야하지? 이론만 알고 머리로 이해하지 못했던 부분들을 깨달으면서 개발 역량을 성장시킬 수 있었습니다. |
| 홍재민 |  이번 HOMS 프로젝트는 저에게 있어 프론트엔드와 백엔드 개발을 통합적으로 경험할 수 있었던 의미 있는 시간이었습니다. 한쪽에만 매몰되지 않고 양쪽 모두를 진행하며 다양한 기술 스택과 실제 개발 사례들을 깊이 있게 접할 수 있었습니다.  <br/>특히, 평소 접하기 어려웠던 B2B(기업 간 거래) 주문 관리 시스템의 전반적인 흐름과 특성을 이해하는 데 큰 도움이 되었습니다. 이는 비즈니스 도메인에 대한 시야를 넓히는 귀중한 기회가 되었습니다.프로젝트 초반에는 9주라는 짧은 기간 안에 계획했던 목표를 달성할 수 있을지에 대한 우려가 있었습니다. <br/> 하지만 시작 후 1~2주간 탄탄한 기반을 마련하고, 팀원들과의 명확한 역할 분담 및 체계적인 일정 계획을 수립했던 것이 최종적인 완성도를 높이는 데 결정적인 역할을 했습니다.개인적으로는 아직 부족한 점이 많다고 느꼈지만, <br/> 이러한 부분들을 팀원들과 긴밀하게 협력하며 보완해나간 덕분에 만족스러운 결과물을 만들어낼 수 있었다고 생각합니다.마지막으로, 이번 프로젝트를 성공적으로 이끌어주신 모든 팀원분들께 진심으로 감사드립니다. |
| 박서준 | 좋은 팀원들과 프로젝트를 함께 하게 되어 걱정과 큰 무리없이 좋은 프로젝트 진행하고 성공적으로 마쳤습니다. <br/> git협업 방식과 웹 프로젝트를 제작하며 전반적인 프로세스를 익히는데 도움이 되었으며 백엔드 역량 뿐만 아니라 프론트엔드 역량 역시 많이 성장 함을 깨닳게 되었습니다. 이번 프로젝트를 통해 배포 과정과 CI/CD 방식도 배우고 혼자라면 어려웠을 DEVOPS영역도 접해볼수 있어 좋은 프로젝트 경험이 되었습니다. 이번 프로젝트를 통해 풀스택 개발자를 지향하는 계기가 되었고 실력 향상에 도움이 되었습니다. |



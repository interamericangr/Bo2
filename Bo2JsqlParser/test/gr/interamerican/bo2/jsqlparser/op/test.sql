select distinct
	reinsurance.CLAIM_NO
	, reinsurance.BRANCH_ID
	, reinsurance.claim_Allocation_Id
	, announce.INCIDENT_DATE
	, claim.DAMAGE_CAUSE as NAME
	, claim.POLICY_NO
	, claim.RECEIPT_NO 
	, policy.underwriting_year
	, reinsurance.TREATY_FACULTATIVE_TYPE as treaty_facultative_type
	, reinsurance.TREATY_ID as treaty_id
    , reinsurance.TREATY_TYPE as treaty_type
	, reinsurance.CESSION_ID as cession_Id 
	, reinsurance.CESSION_TYPE as cession_type
	, reinsurance.ALLOCATION_LINE_TYPE as ALLOCATION_LINE_TYPE
	, reinsurance.LAYER_NO as LAYER_NO
	, reinsurance.reinsurer_contact_no as reinsurer_contact_no
	, reinsurance.participant_contact_no as participant_contact_no
	, coalesce(char(claim.EVENT_ID), '-') as event_id

	, 0.00 as reserve_compensation_at_start
	, 0.00 as reserve_expenses_at_start
	, 0.00 as reserve_interest_at_start
	, 0.00 as reserve_total_at_start
	, 0.00 as cl_allocation_reserves_on_start_date
	
	, 0.00 as payment_compensation
	, 0.00 as payment_expenses
	, 0.00 as payment_total
	, 0.00 as cl_allocation_reserves_on_end_date

	, 0.00 as payment_recoveries
	, 0.00 as reserve_compensation_at_end
	, 0.00 as reserve_expenses_at_end
	, 0.00 as reserve_interest_at_end
	, 0.00 as reserve_total_at_end
	, 0.00 as cl_allocation_paid_on_period
	, claim.STATUS_string as claim_status
	, '' as cover_status
	, brcover.ACCOUNTANCY_BRANCH
	, 'Ευρώ' as currency
	, policy.POLICY_CATEGORY_STRING as policy_category
     , (cst.PNOM||cst.NOM1) as tran_customer
	, policy.PLAN_CD
	, policy.risk_USE_NAME as riskUse
	, policy.ITEM_TP_STRING as item_type,
	claim.item_no,reinsurance.COVER_CD as cover_Cd
	,claim.renewal_no, 0.0 as capital_amount
from X__X.VW1Crein  reinsurance
	join X__X.TB1PBRCV brcover on  brcover.COVER_CD = reinsurance.COVER_CD and brcover.BRANCH_ID = reinsurance.BRANCH_ID
	join X__X.VW1CLAIM claim on reinsurance.CLAIM_NO = claim.CLAIM_NO and reinsurance.BRANCH_ID = claim.BRANCH_ID 
	join X__X.TB1CANCM announce on claim.BRANCH_ID = announce.BRANCH_ID and claim.CLAIM_NO = announce.CLAIM_NO 
	
	join X__X.vm1cptpir policy on claim.BRANCH_ID = policy.BRANCH_ID 
							and claim.CLAIM_NO = policy.CLAIM_NO 
							and policy.POLICY_CATEGORY is not null 
	join (select v.CLAIM_NO,v.BRANCH_ID,v.COVER_CD
				from X__X.TB1CDRSV v 
				where v.CLAIM_NO>0
				and date(v.CREATION_DATE) <= :endDate
				group by v.CLAIM_NO,v.BRANCH_ID,v.COVER_CD
				having sum(v.COMPENSATION_AMOUNT) + sum(v.INTEREST_AMOUNT) + sum(v.EXPENSES_AMOUNT) >0
				union
				select pr.CLAIM_NO,pr.BRANCH_ID,pr.COVER_CD
				from X__X.TB1EPREC pr 
				join X__X.TB1ERCPT r on pr.PAYMENT_RECEIPT_ID=r.PAYMENT_RECEIPT_ID
				where r.RECEIPT_STATUS in (3,6)
				and r.ISSUE_DATE <= 'endDate'
				and r.ISSUE_DATE >= 'startDate'
				group by pr.CLAIM_NO,pr.BRANCH_ID,pr.COVER_CD) as reserve 
				on claim.claim_no = reserve.claim_no and claim.branch_id = reserve.branch_id
				and reinsurance.cover_cd = reserve.cover_cd
	left outer join X__X.TB1ITRCU tranCust on claim.BRANCH_ID = tranCust.BRANCH_ID
							and claim.POLICY_NO = tranCust.POLICY_NO 
							and claim.RECEIPT_NO = tranCust.RECEIPT_NO and policy.POLICY_CATEGORY = 3 
	left outer join X__X.TB1ICUST cst on tranCust.NONR = cst.NONR  
where claim.CLAIM_NO > 0 
and claim.BRANCH_ID =:branchId
and reinsurance.WORK_DATE <= 'endDate' 
and reinsurance.IS_CLAIM_PAID_ALLOCATION = 0
and (reinsurance.CLAIM_PAID_AMOUNT <> 0 or reinsurance.CLAIM_RESERVES_AMOUNT <> 0)

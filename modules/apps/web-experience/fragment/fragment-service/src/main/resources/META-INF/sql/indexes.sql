create unique index IX_536510F5 on FragmentCollection (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_DDB6278B on FragmentEntry (fragmentCollectionId, status);
create unique index IX_18F9DFE on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$]);

create index IX_CC38473A on FragmentEntryInstanceLink (groupId, fragmentEntryId);
create index IX_2B2B7AB7 on FragmentEntryInstanceLink (groupId, layoutPageTemplateEntryId);
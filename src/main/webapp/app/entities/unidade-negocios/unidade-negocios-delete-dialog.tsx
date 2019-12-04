import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './unidade-negocios.reducer';

export interface IUnidadeNegociosDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UnidadeNegociosDeleteDialog extends React.Component<IUnidadeNegociosDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.unidadeNegociosEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { unidadeNegociosEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="agamottoApp.unidadeNegocios.delete.question">
          <Translate contentKey="agamottoApp.unidadeNegocios.delete.question" interpolate={{ id: unidadeNegociosEntity.id }}>
            Are you sure you want to delete this UnidadeNegocios?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-unidadeNegocios" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ unidadeNegocios }: IRootState) => ({
  unidadeNegociosEntity: unidadeNegocios.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UnidadeNegociosDeleteDialog);

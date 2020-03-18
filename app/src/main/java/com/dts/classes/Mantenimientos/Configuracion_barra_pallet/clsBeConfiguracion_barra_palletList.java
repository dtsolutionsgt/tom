package com.dts.classes.Mantenimientos.Configuracion_barra_pallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeConfiguracion_barra_palletList {
    @ElementList(inline=true)
    public List<clsBeConfiguracion_barra_pallet> items = new List<clsBeConfiguracion_barra_pallet>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeConfiguracion_barra_pallet> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeConfiguracion_barra_pallet clsBeConfiguracion_barra_pallet) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeConfiguracion_barra_pallet> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeConfiguracion_barra_pallet> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeConfiguracion_barra_pallet get(int index) {
            return null;
        }

        @Override
        public clsBeConfiguracion_barra_pallet set(int index, clsBeConfiguracion_barra_pallet element) {
            return null;
        }

        @Override
        public void add(int index, clsBeConfiguracion_barra_pallet element) {

        }

        @Override
        public clsBeConfiguracion_barra_pallet remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeConfiguracion_barra_pallet> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeConfiguracion_barra_pallet> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeConfiguracion_barra_pallet> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}